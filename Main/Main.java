import java.awt.Color;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        double cost;
        double rate;
        double tax;
        boolean phone = true;

        // gets the time of the day;
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime time = ZonedDateTime.now(zone);
        int hours = time.getHour();

        DecimalFormat moneyFormat = new DecimalFormat("##0.00");

        // uses a loop to ensure that the user can keep calling as long as he/she has
        // not closed the program
        while (phone) {
            UIManager.put("Button.background", Color.GREEN);
            UIManager.put("OptionPane.okButtonText", "CALL");
            // an input dialog for the user to enter the number
            String myNumber = JOptionPane.showInputDialog(null,
                    hours + ":00hrs" + "  Enter Phone Number\n you wnat to call:", "07");

            // initialize an integer to store the length of the number the user has entered
            int lengthMyNumber = myNumber.length();
            if (lengthMyNumber == 10 || lengthMyNumber == 13) {// checks if a number is from same or other network

                ZonedDateTime timeCalled = ZonedDateTime.now(zone);
                DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                String DateTime = timeCalled.format(myFormat);

                String regex = "07[1234567]";
                Pattern testRegex = Pattern.compile(regex);
                Matcher matcher = testRegex.matcher(myNumber);
                boolean sameNetwork = matcher.find();

                if (sameNetwork) {// call from the same network
                    int timeOfDay = time.getHour();
                    boolean day = (timeOfDay > 5 && timeOfDay < 19);

                    if (day) {// call is made during the day
                        rate = 4;
                        UIManager.put("Button.background", Color.red);
                        UIManager.put("OptionPane.okButtonText", "END");
                        JOptionPane.showMessageDialog(null,
                                "(Day Time)\nStart at:" + DateTime + "\nCalling Same Network.... " + myNumber);
                        ZonedDateTime timeCalledday = ZonedDateTime.now(zone);
                        DateTimeFormatter myFormatday = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String DateTimeday = timeCalledday.format(myFormatday);

                        Duration duration = Duration.between(timeCalled, timeCalledday);
                        double durationSec = duration.getSeconds();
                        double minutes = durationSec / 60;

                        if (minutes > 2) {
                            cost = rate * minutes * 1.16;
                        } else {
                            cost = rate * minutes;
                        }

                        double amount;
                        amount = minutes * rate;
                        tax = cost - amount;
                        UIManager.put("OptionPane.okButtonText", "ok");
                        UIManager.put("Button.background", Color.cyan);
                        JOptionPane.showMessageDialog(null,
                                "Call ended on: " + DateTimeday + "\nTime: " + duration.getSeconds() + "  seconds "
                                        + "\nCost:  " + moneyFormat.format(cost) + "\n VAT: "
                                        + moneyFormat.format(tax));

                    } else {// call is made at night

                        rate = 3;
                        UIManager.put("Button.background", Color.red);

                        UIManager.put("OptionPane.okButtonText", "END");
                        JOptionPane.showMessageDialog(null,
                                "(night)" + "\nStart at: " + DateTime + "\nCalling Same Network.... " + myNumber);
                        ZonedDateTime timeCallednight = ZonedDateTime.now(zone);
                        DateTimeFormatter myFormatnight = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String DateTimenight = timeCallednight.format(myFormatnight);

                        Duration duration = Duration.between(timeCalled, timeCallednight);
                        double durationSec = duration.getSeconds();
                        double minutes = durationSec / 60;

                        if (minutes > 2) {
                            cost = rate * minutes * 1.16;

                        } else {
                            cost = rate * minutes;
                        }

                        double amount;
                        amount = minutes * rate;
                        tax = cost - amount;
                        UIManager.put("Button.background", Color.cyan);
                        UIManager.put("OptionPane.okButtonText", "ok");
                        JOptionPane.showMessageDialog(null,
                                "Call ended at: " + DateTimenight + "\nTime: " + duration.getSeconds() + " seconds "
                                        + "\nCost: " + moneyFormat.format(cost) + "\n VAT: " + moneyFormat.format(tax));
                    }

                } else {// the call is from a different network
                    UIManager.put("Button.background", Color.red);

                    UIManager.put("OptionPane.okButtonText", "END");
                    JOptionPane.showMessageDialog(null,
                            "Start at: " + DateTime + "\nCalling Other Network.... " + myNumber);
                    rate = 5;
                    ZonedDateTime timeCalledother = ZonedDateTime.now(zone);
                    DateTimeFormatter myFormatother = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String DateTimeother = timeCalledother.format(myFormatother);
                    Duration duration = Duration.between(timeCalled, timeCalledother);
                    double durationSec = duration.getSeconds();
                    double minutes = durationSec / 60;

                    if (minutes > 2) {
                        cost = rate * minutes * 1.16;
                    } else {
                        cost = rate * minutes;
                    }

                    double amount;
                    amount = minutes * rate;
                    tax = cost - amount;
                    UIManager.put("Button.background", Color.cyan);
                    UIManager.put("OptionPane.okButtonText", "ok");
                    JOptionPane.showMessageDialog(null,
                            "Call end at: " + DateTimeother + "\nTime: " + duration.getSeconds() + " seconds  "
                                    + "\n Cost: " + moneyFormat.format(cost) + "\n VAT: " + moneyFormat.format(tax));
                }

            } else { // when the number is not equal to 10 digits

                JOptionPane.showMessageDialog(null, "Invalid Number\n(Number should Have 10 Digits).");
                continue;
            }

        }
    }

}
