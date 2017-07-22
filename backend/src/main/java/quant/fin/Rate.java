package quant.fin;

import java.math.BigDecimal;
import java.time.LocalDate;


public final class Rate {

    private final LocalDate date;
    private final BigDecimal rate;


    public Rate(LocalDate date, BigDecimal rate) {
        this.date = date;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
