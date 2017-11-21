package threads.cF.usage;

public class Discount {
    
	public enum Code {    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;
        Code(int percentage) {
            this.percentage = percentage;
        }
    }
    
    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + String.format(" price is %.2f ", Discount.apply(quote.getPrice(), quote.getDiscountCode()));
    }

    private static double apply(double price, Code code) {
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return (price * (100 - code.percentage)) / 100;
    }
}