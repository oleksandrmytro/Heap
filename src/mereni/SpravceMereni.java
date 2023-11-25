package mereni;

import list.AbstrDoubleList;
import list.IAbstrDoubleList;
import list.ListException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Locale;
import static mereni.enumPozice.AKTUALNI;
import static mereni.enumPozice.NASLEDNIK;
import static mereni.enumPozice.POSLEDNI;
import static mereni.enumPozice.PREDCHUDCE;
import static mereni.enumPozice.PRVNI;

public class SpravceMereni {

    private IAbstrDoubleList<Mereni> seznamMereni = new AbstrDoubleList<>();

    public void vlozMereni(Mereni mereni, enumPozice pozice, IAbstrDoubleList<Mereni> seznamMereni) throws ListException {
        switch (pozice) {
            case PRVNI ->
                seznamMereni.vlozPrvni(mereni);
            case POSLEDNI ->
                seznamMereni.vlozPosledni(mereni);
            case NASLEDNIK ->
                seznamMereni.vlozNaslednika(mereni);
            case PREDCHUDCE ->
                seznamMereni.vlozPredchudce(mereni);
        }
    }

    public void zpristupniMereni(enumPozice pozice, IAbstrDoubleList<Mereni> seznamMereni) throws ListException {
        switch (pozice) {
            case PRVNI ->
                seznamMereni.zpristupniPrvni();
            case POSLEDNI ->
                seznamMereni.zpristupniPosledni();
            case AKTUALNI ->
                seznamMereni.zpristupniAktualni();
            case NASLEDNIK ->
                seznamMereni.zpristupniNaslednika();
            case PREDCHUDCE ->
                seznamMereni.zpristupniPredchudce();
        }
    }

    public void odeberMereni(enumPozice pozice, IAbstrDoubleList<Mereni> seznamMereni) throws ListException {
        switch (pozice) {
            case PRVNI ->
                seznamMereni.odeberPrvni();
            case POSLEDNI ->
                seznamMereni.odeberPosledni();
            case AKTUALNI ->
                seznamMereni.odeberAktualni();
            case NASLEDNIK ->
                seznamMereni.odeberNaslednika();
            case PREDCHUDCE ->
                seznamMereni.odeberPredchudce();
        }
    }

    public Iterator<Mereni> iterator() {
        return seznamMereni.iterator();
    }

    public IAbstrDoubleList<Mereni> MereniDen(int idSenzoru, LocalDate datum) {
        IAbstrDoubleList<Mereni> mereniDne = new AbstrDoubleList<>();
        Iterator<Mereni> iterator = seznamMereni.iterator();

        while (iterator.hasNext()) {
            Mereni mereni = iterator.next();
            if (mereni.getIdSenzor() == idSenzoru && mereni.getCasMereni().toLocalDate().equals(datum)) {
                mereniDne.vlozPosledni(mereni);
            }
        }
        return mereniDne;
    }

    public double maxSpotreba(int idSenzoru, LocalDateTime datumOd, LocalDateTime datumDo, IAbstrDoubleList<Mereni> seznamMereni) {
        Iterator<Mereni> iterator = seznamMereni.iterator();
        double maxSpotreba = 0.0;

        while (iterator.hasNext()) {
            Mereni mereni = iterator.next();
            if (mereni.getIdSenzor() == idSenzoru && mereni.getCasMereni().isAfter(datumOd)
                    && mereni.getCasMereni().isBefore(datumDo)) {
                double spotreba = 0.0;
                if (mereni instanceof MereniElektrika) {
                    MereniElektrika mereniElektrika = (MereniElektrika) mereni;
                    spotreba = mereniElektrika.getSpotrebaVT() + mereniElektrika.getSpotrebaNT();
                } else if (mereni instanceof MereniVoda) {
                    MereniVoda mereniVoda = (MereniVoda) mereni;
                    spotreba = mereniVoda.getSpotrebaM3();
                }

                if (spotreba > maxSpotreba) {
                    maxSpotreba = spotreba;
                }
            }
        }
        return maxSpotreba;
    }

    public double prumerSpotreba(int idSenzoru, LocalDateTime datumOd, LocalDateTime datumDo, IAbstrDoubleList<Mereni> seznamMereni) {
        Iterator<Mereni> iterator = seznamMereni.iterator();
        double sumaSpotreba = 0.0;
        int count = 0;

        while (iterator.hasNext()) {
            Mereni mereni = iterator.next();
            if (mereni.getIdSenzor() == idSenzoru && mereni.getCasMereni().isAfter(datumOd)
                    && mereni.getCasMereni().isBefore(datumDo)) {
                double spotreba = 0.0;
                if (mereni instanceof MereniElektrika) {
                    MereniElektrika mereniElektrika = (MereniElektrika) mereni;
                    spotreba = mereniElektrika.getSpotrebaVT() + mereniElektrika.getSpotrebaNT();
                } else if (mereni instanceof MereniVoda) {
                    MereniVoda mereniVoda = (MereniVoda) mereni;
                    spotreba = mereniVoda.getSpotrebaM3();
                }

                sumaSpotreba += spotreba;
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return Double.parseDouble(String.format( Locale.US, "%.2f", sumaSpotreba / count)); 
    }

}
