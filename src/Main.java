interface PurchaseHandler {
    void handleRequest(MobilePhoneType type);

    void setNextHandler(PurchaseHandler nextHandler);

}

class MobilePhone {
    private MobilePhoneType type;

    public MobilePhone(MobilePhoneType type) {
        this.type = type;
    }

    public MobilePhoneType getType() {
        return type;
    }
}
enum MobilePhoneType {
    BASIC,
    BUDGET,
    PREMIUM
}

class BasicPhoneHandler implements PurchaseHandler {
    private PurchaseHandler nextHandler;

    @Override
    public void setNextHandler(PurchaseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(MobilePhoneType type) {
        if (type == MobilePhoneType.BASIC) {
            System.out.println("Оформлено замовлення на основний мобільний телефон");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(type);
        }
    }

}

class BudgetPhoneHandler implements PurchaseHandler {
    private PurchaseHandler nextHandler;


    @Override
    public void setNextHandler(PurchaseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handleRequest(MobilePhoneType type) {
        if (type == MobilePhoneType.BUDGET) {
            System.out.println("Оформлено замовлення на бюджетний мобільний телефон");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(type);
        }
    }

}

class PremiumPhoneHandler implements PurchaseHandler {

    private PurchaseHandler nextHandler;
    @Override
    public void handleRequest(MobilePhoneType type) {
        if (type == MobilePhoneType.PREMIUM) {
            System.out.println("Оформлено замовлення на преміальний мобільний телефон");
        } else {
            nextHandler.handleRequest(type);
            System.out.println("Даний тип мобільного телефону не доступний для замовлення в даному магазині");
        }
    }

    @Override
    public void setNextHandler(PurchaseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}

class PurchaseManager {
    private PurchaseHandler chain;

    public PurchaseManager() {
        chain = new BasicPhoneHandler();
        BudgetPhoneHandler budgetHandler = new BudgetPhoneHandler();
        PurchaseHandler premiumHandler = new PremiumPhoneHandler();

        chain.setNextHandler(budgetHandler);
        budgetHandler.setNextHandler(premiumHandler);
    }

    public void purchaseMobilePhone(MobilePhone mobilePhone) {
        chain.handleRequest(mobilePhone.getType());
    }
}

public class Main {
    public static void main(String[] args) {
        MobilePhone basicPhone = new MobilePhone(MobilePhoneType.BASIC);
        MobilePhone budgetPhone = new MobilePhone(MobilePhoneType.BUDGET);
        MobilePhone premiumPhone = new MobilePhone(MobilePhoneType.PREMIUM);

        PurchaseManager purchaseManager = new PurchaseManager();

        purchaseManager.purchaseMobilePhone(basicPhone);
        purchaseManager.purchaseMobilePhone(budgetPhone);
        purchaseManager.purchaseMobilePhone(premiumPhone);
    }
}
