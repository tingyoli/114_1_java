public class Account {
    private static int accountCount = 0;
    // 帳戶號碼
    private String accountNumber;
    private String ownerName;
    // 帳戶餘額
    private double balance;

    /**
     * 帳戶建構子
     * @param accountNumber 帳戶號碼
     \\* @param accountHolder 帳戶持有人（目前未使用）
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber,String ownerName, double initialBalance){
        this.setAccountNumber(accountNumber);
        this.ownerName = ownerName;
        try{
            this.setBalance(initialBalance);
        }catch (IllegalArgumentException e){
            System.out.println("初始餘額錯誤:" + e.getMessage() + ", 將餘額設為0");
        }
        accountCount++;
    }
    public Account(String accountNumber,double initialBalance) {
        this(accountNumber,"未知", initialBalance);
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }
    public String getOwnerName()
    {
        return ownerName;
    }
    /**
     * 存款方法
     * @param amount 存款金額，必須大於0
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount); // 顯示存款金額
        } else {
            System.out.println("Deposit amount must be positive."); // 金額需為正數
        }
    }

    /**
     * 提款方法
     * @param amount 提款金額，必須大於0且小於等於餘額
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount); // 顯示提款金額
        } else if (amount > balance) {
            System.out.println("Insufficient funds."); // 餘額不足
        } else {
            System.out.println("Withdrawal amount must be positive."); // 金額需為正數
        }
    }
    private void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    private void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("餘額不能為負數");
        }
        this.balance = balance;
    }


    /**
     * 以字串方式顯示帳戶資訊
     * @return 帳戶資訊字串
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }


}