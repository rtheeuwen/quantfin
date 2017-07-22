package quant.fin;


public enum CurrencyPair {

    EURUSD{
        @Override
        public String toString(){
            return "EUR/USD";
        }
    }, EURGBP{
        @Override
        public String toString(){
            return "EUR/GBP";
        }
    };
}
