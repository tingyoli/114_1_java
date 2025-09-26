import java.util.Scanner;

public class Account {

    //帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    //帳戶餘額
    private double balance;

    /**
     * *
     * @param accountNumber
     * @param Balance
     */
    public Account(String accountNumber, double Balance){
        this.accountNumber = accountNumber;
        this.balance = Balance;
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber(){
        return accountNumber;}

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance(){
        return balance;
    }
    /**
     * @throws  IllegalArgumentException 若餘額非正數則指出例外
     * @param balance 帳戶餘額
     */


    public void setBalance(double balance){
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        while (attempts < 3) {
            if (balance > 0) {
                this.balance = balance;
                return; // 成功設定餘額，退出方法
            } else {
                attempts++;
                if (attempts == 3) break;
                System.out.print("帳戶餘額必須為正數，請重新輸入: ");
                if (scanner.hasNextDouble()){
                    balance = scanner.nextDouble();
                } else {
                    scanner.next();
                    balance = -1;

                }
            }
        }
        throw new IllegalArgumentException("帳戶餘額必須為正數");

    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則指出例外
     */
    public void deposit(double amount) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        while (attempts < 3) {
            if (balance > 0) {
                this.balance += amount;
                return; // 成功設定餘額，退出方法
            } else {
                attempts++;
                if (attempts == 3) break;
                System.out.println("帳戶餘額必須為正數，請重新輸入: ");
                if (scanner.hasNextDouble()){
                    amount = scanner.nextDouble();
                } else {
                    scanner.next();
                    amount = -1;

                }
            }
        } throw new IllegalArgumentException("存款金額必須為正數");
    }
    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則指出例外
     */
    public void withdraw(double amount){
        Scanner scanner = new Scanner((System.in));
        int attempts = 0;
        while (attempts < 3) {
            if (balance > 0 && amount <= balance) {
                this.balance -= amount;
                return; // 成功設定餘額，退出方法
            } else {
                attempts++;
                if (attempts == 3) break;
                System.out.println("提款金額必不合法，請重新輸入: ");
                if (scanner.hasNextDouble()){
                    amount = scanner.nextDouble();
                } else {
                    scanner.next();
                    amount = -1;

                }
            }
        } throw new IllegalArgumentException("提款金額必不合法");
    }
}
