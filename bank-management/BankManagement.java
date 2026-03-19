package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

class BankAccount {
    private String name;
    private int accNo;
    private double balance;

    public BankAccount(String name, int accNo, double balance) {
        this.name = name;
        this.accNo = accNo;
        this.balance = balance;
    }

    public void deposit(double amount) { balance += amount; }

    public boolean withdraw(double amount) {
        if (amount <= balance) { balance -= amount; return true; }
        return false;
    }

    public double getBalance() { return balance; }
    public String getName() { return name; }
    public int getAccNo() { return accNo; }
}

public class BankGUI extends JFrame implements ActionListener {

    JButton createBtn, depositBtn, withdrawBtn, balanceBtn, transferBtn, closeBtn;

    JPanel cardPanel;
    CardLayout cardLayout;

    JTextField createName, createAcc, createBalance;
    JTextField depositAcc, depositAmt;
    JTextField withdrawAcc, withdrawAmt;
    JTextField balanceAcc;
    JTextField transferFromAcc, transferToAcc, transferAmt;
    JTextField closeAcc;

    JTextArea output;

    HashMap<Integer, BankAccount> accounts = new HashMap<>();

    BankGUI() {
        setTitle("Bank Account Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        // --- Top nav ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 14));
        actionPanel.setBackground(new Color(45, 45, 60));

        createBtn   = makeNavBtn("➕ Create Account");
        depositBtn  = makeNavBtn("💰 Deposit");
        withdrawBtn = makeNavBtn("💸 Withdraw");
        balanceBtn  = makeNavBtn("📊 Check Balance");
        transferBtn = makeNavBtn("🔁 Transfer");
        closeBtn    = makeNavBtn("🗑️ Close Account");

        actionPanel.add(createBtn);
        actionPanel.add(depositBtn);
        actionPanel.add(withdrawBtn);
        actionPanel.add(balanceBtn);
        actionPanel.add(transferBtn);
        actionPanel.add(closeBtn);

        add(actionPanel, BorderLayout.NORTH);

        // --- Center: Card panels ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(240, 242, 248));

        cardPanel.add(buildCreatePanel(),   "CREATE");
        cardPanel.add(buildDepositPanel(),  "DEPOSIT");
        cardPanel.add(buildWithdrawPanel(), "WITHDRAW");
        cardPanel.add(buildBalancePanel(),  "BALANCE");
        cardPanel.add(buildTransferPanel(), "TRANSFER");
        cardPanel.add(buildClosePanel(),    "CLOSE");

        add(cardPanel, BorderLayout.CENTER);

        // --- Bottom: Output ---
        output = new JTextArea(8, 44);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 15));
        output.setBackground(new Color(30, 30, 40));
        output.setForeground(new Color(180, 255, 180));
        output.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Output",
                0, 0, new Font("SansSerif", Font.BOLD, 14)));
        add(scroll, BorderLayout.SOUTH);

        highlightBtn(createBtn);

        setSize(1100, 900);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton makeNavBtn(String label) {
        JButton btn = new JButton(label);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 70, 90));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        btn.addActionListener(this);
        return btn;
    }

    private void highlightBtn(JButton active) {
        for (JButton b : new JButton[]{createBtn, depositBtn, withdrawBtn, balanceBtn, transferBtn, closeBtn})
            b.setBackground(new Color(70, 70, 90));
        active.setBackground(new Color(0, 120, 215));
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(new Color(240, 242, 248));
        p.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        return p;
    }

    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = x; g.gridy = y;
        g.insets = new Insets(14, 14, 14, 14);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return l;
    }

    private JTextField field() {
        JTextField tf = new JTextField(22);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        return tf;
    }

    private JButton submitButton(String text, String cmd) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0, 160, 100));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btn.setActionCommand(cmd);
        btn.addActionListener(this);
        return btn;
    }

    private JPanel buildCreatePanel() {
        JPanel p = buildFormPanel();
        createName    = field();
        createAcc     = field();
        createBalance = field();

        p.add(label("Full Name:"),       gbc(0, 0));
        p.add(createName,                gbc(1, 0));
        p.add(label("Account Number:"),  gbc(0, 1));
        p.add(createAcc,                 gbc(1, 1));
        p.add(label("Initial Balance:"), gbc(0, 2));
        p.add(createBalance,             gbc(1, 2));

        GridBagConstraints g = gbc(0, 3);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        p.add(submitButton("Create Account", "DO_CREATE"), g);
        return p;
    }

    private JPanel buildDepositPanel() {
        JPanel p = buildFormPanel();
        depositAcc = field();
        depositAmt = field();

        p.add(label("Account Number:"), gbc(0, 0));
        p.add(depositAcc,               gbc(1, 0));
        p.add(label("Deposit Amount:"), gbc(0, 1));
        p.add(depositAmt,               gbc(1, 1));

        GridBagConstraints g = gbc(0, 2);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        p.add(submitButton("Deposit", "DO_DEPOSIT"), g);
        return p;
    }

    private JPanel buildWithdrawPanel() {
        JPanel p = buildFormPanel();
        withdrawAcc = field();
        withdrawAmt = field();

        p.add(label("Account Number:"),    gbc(0, 0));
        p.add(withdrawAcc,                 gbc(1, 0));
        p.add(label("Withdrawal Amount:"), gbc(0, 1));
        p.add(withdrawAmt,                 gbc(1, 1));

        GridBagConstraints g = gbc(0, 2);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        p.add(submitButton("Withdraw", "DO_WITHDRAW"), g);
        return p;
    }

    private JPanel buildBalancePanel() {
        JPanel p = buildFormPanel();
        balanceAcc = field();

        p.add(label("Account Number:"), gbc(0, 0));
        p.add(balanceAcc,               gbc(1, 0));

        GridBagConstraints g = gbc(0, 1);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        p.add(submitButton("Check Balance", "DO_BALANCE"), g);
        return p;
    }

    private JPanel buildTransferPanel() {
        JPanel p = buildFormPanel();
        transferFromAcc = field();
        transferToAcc   = field();
        transferAmt     = field();

        p.add(label("From Account No:"), gbc(0, 0));
        p.add(transferFromAcc,           gbc(1, 0));
        p.add(label("To Account No:"),   gbc(0, 1));
        p.add(transferToAcc,             gbc(1, 1));
        p.add(label("Transfer Amount:"), gbc(0, 2));
        p.add(transferAmt,               gbc(1, 2));

        GridBagConstraints g = gbc(0, 3);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        p.add(submitButton("Transfer Money", "DO_TRANSFER"), g);
        return p;
    }

    private JPanel buildClosePanel() {
        JPanel p = buildFormPanel();
        closeAcc = field();

        p.add(label("Account Number:"), gbc(0, 0));
        p.add(closeAcc,                 gbc(1, 0));

        GridBagConstraints g = gbc(0, 1);
        g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;

        JButton btn = submitButton("Close Account", "DO_CLOSE");
        btn.setBackground(new Color(200, 50, 50));
        p.add(btn, g);
        return p;
    }

    private BankAccount getAccount(JTextField field) {
        try {
            int acc = Integer.parseInt(field.getText().trim());
            BankAccount account = accounts.get(acc);
            if (account == null) output.append("❌ Error: Account " + acc + " does not exist.\n");
            return account;
        } catch (NumberFormatException ex) {
            output.append("❌ Error: Invalid account number.\n");
            return null;
        }
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (e.getSource() == createBtn)   { cardLayout.show(cardPanel, "CREATE");   highlightBtn(createBtn);   return; }
        if (e.getSource() == depositBtn)  { cardLayout.show(cardPanel, "DEPOSIT");  highlightBtn(depositBtn);  return; }
        if (e.getSource() == withdrawBtn) { cardLayout.show(cardPanel, "WITHDRAW"); highlightBtn(withdrawBtn); return; }
        if (e.getSource() == balanceBtn)  { cardLayout.show(cardPanel, "BALANCE");  highlightBtn(balanceBtn);  return; }
        if (e.getSource() == transferBtn) { cardLayout.show(cardPanel, "TRANSFER"); highlightBtn(transferBtn); return; }
        if (e.getSource() == closeBtn)    { cardLayout.show(cardPanel, "CLOSE");    highlightBtn(closeBtn);    return; }

        if (cmd.equals("DO_CREATE")) {
            try {
                String name = createName.getText().trim();
                int acc     = Integer.parseInt(createAcc.getText().trim());
                double bal  = Double.parseDouble(createBalance.getText().trim());

                if (name.isEmpty())            { output.append("❌ Error: Name cannot be empty.\n"); return; }
                if (accounts.containsKey(acc)) { output.append("❌ Error: Account " + acc + " already exists.\n"); return; }
                if (bal < 0)                   { output.append("❌ Error: Initial balance cannot be negative.\n"); return; }

                accounts.put(acc, new BankAccount(name, acc, bal));
                output.append("✅ Account created for " + name + " (Acc No: " + acc + ", Balance: ₹" + bal + ")\n");
            } catch (NumberFormatException ex) {
                output.append("❌ Error: Invalid account number or balance.\n");
            }
        }

        else if (cmd.equals("DO_DEPOSIT")) {
            BankAccount account = getAccount(depositAcc);
            if (account == null) return;
            try {
                double amt = Double.parseDouble(depositAmt.getText().trim());
                if (amt <= 0) { output.append("❌ Error: Amount must be positive.\n"); return; }
                account.deposit(amt);
                output.append("✅ Deposited ₹" + amt + " to " + account.getName() + "'s account.\n");
            } catch (NumberFormatException ex) { output.append("❌ Error: Invalid amount.\n"); }
        }

        else if (cmd.equals("DO_WITHDRAW")) {
            BankAccount account = getAccount(withdrawAcc);
            if (account == null) return;
            try {
                double amt = Double.parseDouble(withdrawAmt.getText().trim());
                if (amt <= 0) { output.append("❌ Error: Amount must be positive.\n"); return; }
                if (account.withdraw(amt))
                    output.append("✅ Withdrawn ₹" + amt + " from " + account.getName() + "'s account.\n");
                else
                    output.append("❌ Insufficient balance. Current: ₹" + account.getBalance() + "\n");
            } catch (NumberFormatException ex) { output.append("❌ Error: Invalid amount.\n"); }
        }

        else if (cmd.equals("DO_BALANCE")) {
            BankAccount account = getAccount(balanceAcc);
            if (account == null) return;
            output.append("📊 " + account.getName() + "'s Balance: ₹" + account.getBalance() + "\n");
        }

        else if (cmd.equals("DO_TRANSFER")) {
            BankAccount from = getAccount(transferFromAcc);
            if (from == null) return;
            try {
                int toAccNo    = Integer.parseInt(transferToAcc.getText().trim());
                double amt     = Double.parseDouble(transferAmt.getText().trim());
                BankAccount to = accounts.get(toAccNo);

                if (to == null)                 { output.append("❌ Error: Destination account " + toAccNo + " does not exist.\n"); return; }
                if (from.getAccNo() == toAccNo) { output.append("❌ Error: Cannot transfer to the same account.\n"); return; }
                if (amt <= 0)                   { output.append("❌ Error: Transfer amount must be positive.\n"); return; }
                if (!from.withdraw(amt))        { output.append("❌ Insufficient balance. Current: ₹" + from.getBalance() + "\n"); return; }

                to.deposit(amt);
                output.append("✅ Transferred ₹" + amt + " from " + from.getName() + " → " + to.getName() + "\n");
            } catch (NumberFormatException ex) { output.append("❌ Error: Invalid account number or amount.\n"); }
        }

        else if (cmd.equals("DO_CLOSE")) {
            try {
                int acc = Integer.parseInt(closeAcc.getText().trim());
                BankAccount account = accounts.get(acc);

                if (account == null) { output.append("❌ Error: Account " + acc + " does not exist.\n"); return; }

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to close " + account.getName() + "'s account?\nRemaining Balance: ₹" + account.getBalance(),
                        "Confirm Account Closure",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    accounts.remove(acc);
                    output.append("🗑️ Account " + acc + " (" + account.getName() + ") has been closed.\n");
                    closeAcc.setText("");
                }
            } catch (NumberFormatException ex) {
                output.append("❌ Error: Invalid account number.\n");
            }
        }
    }

    public static void main(String[] args) {
        new BankGUI();
    }
}
