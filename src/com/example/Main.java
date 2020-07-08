package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static ArrayList<Account> accountList = new ArrayList<>();
    private static ArrayList<Transaction> transactionList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Start the main loop of the system
        // This outer label will be labeled as mainLoop so we can break it even within a nested loop / switch
        mainLoop:
        while(true) {
            // Get the choice of the user as to what operation he would like to do
            int choice = promptMenu(input);

            // Perform a function based on the input of the user
            switch(choice) {
                case 1:
                    // Invoke the createAccount method
                    // Input is the Scanner object that will be used by the method
                    createAccount(input);
                    break;
                case 2:
                    // Invoke the deposit method
                    // Input is the Scanner object that will be used by the method
                    deposit(input);
                    break;
                case 3:
                    // Invoke the withdraw method
                    // Input is the Scanner object that will be used by the method
                    withdraw(input);
                    break;
                case 4:
                    // Invoke the inquiry method
                    // Input is the Scanner object that will be used by the method
                    inquiry(input);
                    break;
                case 5:
                    // Invoke the inquiryByDate method
                    // Input is the Scanner object that will be used by the method
                    inquiryByDate(input);
                    break;
                case 6:
                    // Invoke the inquiryByStatus method
                    // Input is the Scanner object that will be used by the method
                    inquiryByStatus(input);
                    break;
                case 7:
                    // Invoke the inquiryByDate method
                    // Input is the Scanner object that will be used by the method
                    showAllAccounts();
                    break;
                case 8:
                    break mainLoop;
            }
        }
    }

    private static int promptMenu(Scanner scanner) {
        try {
            int choice;

            // Show the options to the user
            System.out.println("What would you like to do?");
            System.out.println("[1] New Accounts");
            System.out.println("[2] Deposit");
            System.out.println("[3] Withdrawal");
            System.out.println("[4] Account Inquiry");
            System.out.println("[5] Account Inquiry by Date");
            System.out.println("[6] Inquiry by Status");
            System.out.println("[7] Show All Accounts");
            System.out.println("[8] Exit");

            // Store the user input into a variable
            System.out.print("Choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            // Check if the input is within the range of the valid options
            if(choice >= 1 && choice <= 8) {
                // Show an extra blank line
                System.out.println();
                return choice;
            } else {
                // Throw an exception if the input is invalid
                throw new Exception();
            }
        } catch (Exception e) {
            // If the input includes a nun numeric character, NumberFormatException will be thrown
            // An exception will also be thrown if the input is not between 1-8
            // If an exception is thrown, re-invoke the promptMenu() method
            System.out.println("\nPlease enter a valid option\n");
            return promptMenu(scanner);
        }
    }

    public static int generateAccountNumber() {
        Random random = new Random();
        int min = 111;
        int max = 999;

        while(true) {
            int accountNumber = random.nextInt(max - min) + 111;

            // Check if the account number already exists
            for(Account account : accountList) {
                if(account.getAccountNumber() == accountNumber) {
                    // Continue -> start this code from the start of the loop.
                    continue;
                }
            }

            return accountNumber;
        }
    }

    public static void createAccount(Scanner scanner) {
        try {
            Account account;
            Date date;
            String name, gender, address;
            int accountNumber;
            double openingBalance;

            // Ask the user to enter the account number
//            System.out.print("Enter account number (111-999): ");
//            accountNumber = Integer.parseInt(scanner.nextLine());

            // Validate if the account number is in 111-999 and if it doesn't exists
//            if (accountNumber < 111 || accountNumber > 999) {
//                throw new Exception("Account number must be between 111-999 (inclusive)");
//            } else if (getAccount(accountNumber) != null) {
//                throw new Exception("Account already exists");
//            }

            // Ask the user to enter his/her account name
            System.out.print("Enter name: ");
            name = scanner.nextLine();

            // Ask the user to enter his/her gender
            System.out.print("Enter gender (M/F): ");
            gender = scanner.nextLine();

            // Validate the gender
            if(!gender.equals("M") && !gender.equals("F")) {
                throw new Exception("Gender can only be set to 'M' for male or 'F' for female");
            }

            // Ask the user to enter his/her address
            System.out.print("Enter address: ");
            address = scanner.nextLine();

            // Ask the user to enter his/her opening balance
            System.out.print("Enter opening balance (min: 500): ");
            openingBalance = Double.parseDouble(scanner.nextLine());

            // Validate if the opening balance is at least 500
            if(openingBalance < 500) {
                throw new Exception("Opening balance must be at least 500");
            }

            // Create the account
            accountNumber = generateAccountNumber();
            date = new Date(System.currentTimeMillis());
            account = new Account(accountNumber, name, gender, address, openingBalance, date, "OP");
            accountList.add(account);

            showAccountDetails(account);
        } catch(NumberFormatException numberFormatException) {
            System.out.println("\nAn error occurred while trying to process your input.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void deposit(Scanner scanner) {
        try {
            Account account;
            Date depositDate;
            int accountNumber;
            String date;
            double amount;

            // Create the date format.
            // Set leniet to false so an exception will be thrown for invalid number
            // Eg. 13 for Month will throw an exception
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);

            // Ask the user to enter the account number
            System.out.print("Enter account number (111-999): ");
            accountNumber = Integer.parseInt(scanner.nextLine());
            account = getAccount(accountNumber);

            // Validate if the account number is in 111-999, if it exists, or if it is active
            if (accountNumber < 111 || accountNumber > 999) {
                throw new Exception("Account number must be between 111-999 (inclusive)");
            } else if (account == null) {
                throw new Exception("No account found with the number " + accountNumber);
            } else if (!account.getStatus().equals("OP")) {
                throw new Exception("This account is not active");
            }

            // Ask the user to enter the deposit date
            System.out.print("Enter deposit date (mm/dd/yyyy): ");
            date = scanner.nextLine();

            // Convert the string input into a Date object
            depositDate = dateFormat.parse(date);

            // Ask the user to enter the deposit amount
            System.out.print("Enter amount to deposit: ");
            amount = Double.parseDouble(scanner.nextLine());

            // Validate that the amount is at least 1
            if(amount <= 0) {
                throw new Exception("Cannot deposit amount that is less than 1");
            } else {
                // Set the new balance of the account
                account.setBalance(account.getBalance() + amount);
            }

            // Add this transaction to the list of transactions
            transactionList.add(new Transaction(account, "Deposit ", depositDate, amount));

            // Show the deposit details
            System.out.println("\nDeposit details");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Owner: " + account.getName());
            System.out.println("Deposit Date: " + date);
            System.out.println("Cash Deposit Amount: " + amount);
            System.out.println("Updated Account Balance: " + account.getBalance());
            System.out.println("Status (Open/Closed): OPEN");
            System.out.println();
        } catch (ParseException parseException) {
            System.out.println("\nInvalid date format mm/dd/yyyy\n");
        } catch(NumberFormatException numberFormatException) {
            System.out.println("\nAn error occurred while trying to process your input.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void withdraw(Scanner scanner) {
        try {
            Account account;
            Date withdrawDate;
            int accountNumber;
            String date;
            double amount;

            // Create the date format.
            // Set leniet to false so an exception will be thrown for invalid number
            // Eg. 13 for Month will throw an exception
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);

            // Ask the user to enter the account number
            System.out.print("Enter account number (111-999): ");
            accountNumber = Integer.parseInt(scanner.nextLine());
            account = getAccount(accountNumber);

            // Validate if the account number is in 111-999, if it exists, or if it is active
            if (accountNumber < 111 || accountNumber > 999) {
                throw new Exception("Account number must be between 111-999 (inclusive)");
            } else if (account == null) {
                throw new Exception("No account found with the number " + accountNumber);
            } else if (!account.getStatus().equals("OP")) {
                throw new Exception("This account is not active");
            }

            // Ask the user to enter the withdraw date
            System.out.print("Enter withdraw date (mm/dd/yyyy): ");
            date = scanner.nextLine();

            // Convert the string input into a Date object
            withdrawDate = dateFormat.parse(date);

            // Ask the user to enter the withdraw amount
            System.out.print("Enter amount to be withdrawn: ");
            amount = Double.parseDouble(scanner.nextLine());

            // Validate the amount to be withdrawn
            if(amount <= 0) {
                throw new Exception("Cannot withdraw an amount that is less than 1");
            } else if(amount > account.getBalance()) {
                throw new Exception("Account does not have enough funds");
            } else {
                // Set the new balance of the account
                account.setBalance(account.getBalance() - amount);
            }

            // Add this transaction to the list of transactions
            transactionList.add(new Transaction(account, "Withdraw", withdrawDate, amount));

            // Show the transaction details
            System.out.println("\nWithdraw details");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Owner: " + account.getName());
            System.out.println("Date withdrawn: " + date);
            System.out.println("Amount withdrawn: " + amount);
            System.out.println("Updated Account Balance: " + account.getBalance());
            System.out.println("Status (Open/Closed): OPEN");
            System.out.println();
        } catch (ParseException parseException) {
            System.out.println("\nInvalid date format mm/dd/yyyy\n");
        } catch(NumberFormatException numberFormatException) {
            System.out.println("\nAn error occurred while trying to process your input.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void inquiry(Scanner scanner) {
        try {
            ArrayList<Transaction> transactions;
            Account account;
            int accountNumber;

            // Ask the user to enter the account number
            System.out.print("Enter account number (111-999): ");
            accountNumber = Integer.parseInt(scanner.nextLine());
            account = getAccount(accountNumber);

            // Validate if the account number is in 111-999, if it exists, or if it is active
            if (accountNumber < 111 || accountNumber > 999) {
                throw new Exception("Account number must be between 111-999 (inclusive)");
            } else if (account == null) {
                throw new Exception("No account found with the number " + accountNumber);
            }

            // Get all the transactions under this account
            transactions = getTransactions(account);

            // Show the account details
            showAccountDetails(account);

            // Show the transaction details
            showTransactionDetails(transactions, null, null);
        } catch(NumberFormatException numberFormatException) {
            System.out.println("\nAn error occurred while trying to process your input.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void inquiryByDate(Scanner scanner) {
        try {
            ArrayList<Transaction> transactions;
            Account account;
            int accountNumber;
            Date startDate, endDate;

            // Create the date format.
            // Set leniet to false so an exception will be thrown for invalid number
            // Eg. 13 for Month will throw an exception
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);

            // Ask the user to enter the account number
            System.out.print("Enter account number (111-999): ");
            accountNumber = Integer.parseInt(scanner.nextLine());
            account = getAccount(accountNumber);

            // Validate if the account number is in 111-999, if it exists, or if it is active
            if (accountNumber < 111 || accountNumber > 999) {
                throw new Exception("Account number must be between 111-999 (inclusive)");
            } else if (account == null) {
                throw new Exception("No account found with the number " + accountNumber);
            }

            // Ask the user to input a date and parse it into a Date object
            System.out.print("From date (mm/dd/yyyy): ");
            startDate = dateFormat.parse(scanner.nextLine());

            // Ask the user to input a date and parse it into a Date object
            System.out.print("To date (mm/dd/yyyy): ");
            endDate = dateFormat.parse(scanner.nextLine());

            // Check if the given dates are valid
            if(endDate.before(startDate)) {
                throw new Exception("'To' cannot be a date before 'from'");
            }

            // Get all the transactions under this account during a certain period
            transactions = getTransactions(account, startDate, endDate);

            // Show the account details
            showAccountDetails(account);

            // Show the transaction details
            showTransactionDetails(transactions, startDate, endDate);
        } catch (ParseException parseException) {
            System.out.println("\nInvalid date format mm/dd/yyyy\n");
        } catch(NumberFormatException numberFormatException) {
            System.out.println("\nAn error occurred while trying to process your input.\n");
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void inquiryByStatus(Scanner scanner) {
        try {
            ArrayList<Account> accountMatched = new ArrayList<>();
            String status;

            // Ask the user to enter the desired status
            System.out.print("Enter status (OP/CL): ");
            status = scanner.nextLine();

            // Ensure that the entered status is valid
            if(!status.equals("OP") && !status.equals("CL")) {
                throw new Exception("Invalid status, please enter either 'OP' or 'CL'");
            }

            // Show the list of accounts that matches
            System.out.println("\nStatus (Open/Closed): " + status);
            System.out.println("Account No.\tName\t\tAddress\t\tBalance");

            // Create a list for accounts with the given status
            for(Account account : accountList) {
                if(account.getStatus().equals(status)) {
                    accountMatched.add(account);
                }
            }

            // Show each account details
            if(accountMatched.size() == 0) {
                System.out.println("\tNo account found with this status");
            } else {
                for(Account account : accountMatched) {
                    System.out.print("\t" + account.getAccountNumber());
                    System.out.print("\t\t" + account.getName());
                    System.out.print("\t" + account.getAddress());
                    System.out.println("\t\t" + account.getBalance());
                }
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }
    }

    public static void showAllAccounts() {
        if(accountList.size() > 0) {
            // Sort the list by balance in descending order
            Collections.sort(accountList, new Comparator<Account>() {
                @Override
                public int compare(Account o1, Account o2) {
                    // Return 0 if the balance of 2 account being compared is equal
                    if (o1.getBalance() == o2.getBalance()) {
                        return 0;
                    }
                    // Return 1 if the first account has a lesser balance
                    // This will put the first account at the top of the second account
                    else if (o1.getBalance() < o2.getBalance()) {
                        return 1;
                    }
                    // Return -1 if the second account has a lesser balance
                    // This will put the second account at the top of the first account
                    else {
                        return -1;
                    }
                }
            });

            // Show the list of all accounts
            System.out.println("Account No.\tName\t\tAddress\t\tBalance");

            if (accountList.size() == 0) {
                System.out.println("\tNo account found with this status");
            } else {
                for (Account account : accountList) {
                    System.out.print("\t" + account.getAccountNumber());
                    System.out.print("\t\t" + account.getName());
                    System.out.print("\t" + account.getAddress());
                    System.out.println("\t\t" + account.getBalance());
                }
            }
        } else {
            System.out.println("There is currently no registered account on the system.");
        }

        System.out.println();
    }

    public static void showAccountDetails(Account account) {
        // Create the date format.
        // Set leniet to false so an exception will be thrown for invalid number
        // Eg. 13 for Month will throw an exception
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);

        System.out.println("\nAccount No.: " + account.getAccountNumber());
        System.out.println("Account Owner: " + account.getName());
        System.out.println("Gender: " + account.getGender());
        System.out.println("Date Account Opened: " + dateFormat.format(account.getDateOpened()));
        System.out.println("Address: " + account.getAddress());
        System.out.println("Account Balance: " + account.getBalance());

        if(account.getStatus().equals("OP")) {
            System.out.println("Status (Open/Closed): Open");
        } else {
            System.out.println("Status (Open/Closed): Closed");
        }

        System.out.println();
    }

    public static void showTransactionDetails(ArrayList<Transaction> transactions, Date start, Date end) {
        if(start == null && end == null) {
            System.out.println("\t\t\t\t\tAccount Details");
        } else {
            // Create the date format.
            // Set leniet to false so an exception will be thrown for invalid number
            // Eg. 13 for Month will throw an exception
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
            dateFormat.setLenient(false);

            // Convert start and end into a desired date format
            String from = dateFormat.format(start);
            String to = dateFormat.format(end);
            System.out.println("Account Details " + from + " (FROM) - " + to + " (To)");
        }

        // Show the list of transactions
        System.out.print("Transaction Date");
        System.out.print("\tTransaction Type");
        System.out.println("\tAmount");

        if(transactions.size() == 0) {
            System.out.println("\n\tThis account has no transaction records");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setLenient(false);
            for(Transaction transaction : transactions) {
                System.out.print(" " + dateFormat.format(transaction.getDate()));
                System.out.print("\t\t\t\t" + transaction.getType());
                System.out.println("\t\t" + transaction.getAmount());
            }
        }

        System.out.println();
    }

    public static Account getAccount(int accountNumber) {
        // Iterate through the list of account and return it if it already exists
        for(Account account : accountList) {
            // Return the account if the account number match
            if(account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        // Return null if the account does not yet exists
        return null;
    }

    public static ArrayList<Transaction> getTransactions(Account account) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Find all of the transactions done under the given account.
        for(Transaction transaction : transactionList) {
            if(transaction.getAccount().equals(account)) {
                transactions.add(transaction);
            }
        }

        return transactions;
    }

    public static ArrayList<Transaction> getTransactions(Account account, Date start, Date end) {
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Iterate through all the transaction under this account
        for(Transaction transaction : getTransactions(account)) {
            // Get the date from the current transaction
            Date transactionDate = transaction.getDate();

            // Only add transactions that is not before the start date and not after end date
            if(!transactionDate.before(start) && !transactionDate.after(end)) {
                transactions.add(transaction);
            }
        }

        // Return the list of transaction that matches the requirements.
        return transactions;
    }
}
