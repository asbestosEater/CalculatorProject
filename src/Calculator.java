import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Calculator {
    JFrame frame;
    JPanel left;
    JPanel right;

    JTextArea typingSpace;
    DrawingPanel renderSpace;
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JPanel row4;
    JPanel row5;
    JPanel row6;


    ElementButton x;
    ElementButton leftPrths;
    ElementButton rightPrths;
    JButton cut;
    String storage;
    ElementButton divide;

    JButton y;
    ElementButton seven;
    ElementButton eight;
    ElementButton nine;
    ElementButton multiply;

    JButton graph;
    ElementButton log;
    ElementButton four;
    ElementButton five;
    ElementButton six;
    ElementButton subtract;

    JButton clear;
    ElementButton one;
    ElementButton two;
    ElementButton three;
    ElementButton add;

    JButton del;
    ElementButton zero;
    ElementButton decimal;
    JButton paste;
    JButton equal;

    public Calculator()
    {
        frame = new JFrame("Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        right = new JPanel();
        right.setLayout(new BorderLayout());
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);

        typingSpace = new JTextArea();
        left.add(typingSpace);

        renderSpace = new DrawingPanel();
        right.add(renderSpace, BorderLayout.WEST);

        row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));

        left.add(row1);

        row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        y = new JButton("y=");
        y.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typingSpace.setText("y=");
            }
        });
        row2.add(y);
        leftPrths = new ElementButton(typingSpace, "(");
        row2.add(leftPrths);
        rightPrths = new ElementButton(typingSpace, ")");
        row2.add(rightPrths);
        cut = new JButton("cut");
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storage = typingSpace.getText();
                typingSpace.setText("");
            }
        });
        row2.add(cut);
        divide = new ElementButton(typingSpace, "/");
        row2.add(divide);
        left.add(row2);

        row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        x = new ElementButton(typingSpace, "x");
        row3.add(x);
        seven = new ElementButton(typingSpace, "7");
        row3.add(seven);
        eight = new ElementButton(typingSpace, "8");
        row3.add(eight);
        nine = new ElementButton(typingSpace, "9");
        row3.add(nine);
        multiply = new ElementButton(typingSpace, "*");
        row3.add(multiply);
        left.add(row3);

        row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        graph = new JButton("graph");
        graph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plugged1 = typingSpace.getText().replace("x", "(0-10)");
                System.out.println(plugged1);
                int y1 = (int)evaluate(plugged1.substring(2));
                String plugged2 = typingSpace.getText().replace("x", "" + 10);
                int y2 = (int)evaluate(plugged2.substring(2));
                renderSpace.setYs(-1 * y1, -1 * y2);
                renderSpace.setIsGraph(true);
                renderSpace.rep();
            }
        });
        row4.add(graph);
        four = new ElementButton(typingSpace, "4");
        row4.add(four);
        five = new ElementButton(typingSpace, "5");
        row4.add(five);
        six = new ElementButton(typingSpace, "6");
        row4.add(six);
        subtract = new ElementButton(typingSpace, "-");
        row4.add(subtract);
        left.add(row4);

        row5 = new JPanel();
        row5.setLayout(new BoxLayout(row5, BoxLayout.X_AXIS));
        clear = new JButton("clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typingSpace.setText("");
            }
        });
        row5.add(clear);
        one = new ElementButton(typingSpace, "1");
        row5.add(one);
        two = new ElementButton(typingSpace, "2");
        row5.add(two);
        three = new ElementButton(typingSpace, "3");
        row5.add(three);
        add = new ElementButton(typingSpace, "+");
        row5.add(add);
        left.add(row5);

        row6 = new JPanel();
        row6.setLayout(new BoxLayout(row6, BoxLayout.X_AXIS));
        del = new JButton("del");
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typingSpace.setText(typingSpace.getText().substring(0, typingSpace.getText().length() - 1));
            }
        });
        row6.add(del);
        decimal = new ElementButton(typingSpace, ".");
        row6.add(decimal);
        zero = new ElementButton(typingSpace, "0");
        row6.add(zero);
        paste = new JButton("paste");
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typingSpace.setText(typingSpace.getText() + storage);
            }
        });
        row6.add(paste);
        equal = new JButton("=");
        equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typingSpace.setText("" + evaluate(typingSpace.getText()));
            }
        });
        row6.add(equal);
        left.add(row6);

        frame.pack();
        frame.setVisible(true);
    }

    public static double evaluate(String expression) {
        Deque<String> polish = convertPolish(expression);
        return evalPolish(polish);
    }

    private static Deque<String> convertPolish(String expression) {
        Deque<String> polish = new ArrayDeque<>();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder numBuilder = new StringBuilder();
                numBuilder.append(c);
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    numBuilder.append(expression.charAt(++i));
                }
                polish.addLast(numBuilder.toString());
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    polish.addLast(String.valueOf(stack.pop()));
                }
                stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && pemdas(c) <= pemdas(stack.peek())) {
                    polish.addLast(String.valueOf(stack.pop()));
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            polish.addLast(String.valueOf(stack.pop()));
        }

        return polish;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int pemdas(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static double evalPolish(Deque<String> polish) {
        Deque<Double> stack = new ArrayDeque<>();
        while (!polish.isEmpty()) {
            String section = polish.removeFirst();
            if (section.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(section));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (section) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                }
            }
        }
        return stack.pop();
    }
}

class DrawingPanel extends JPanel {

    boolean isGraph;
    int y1;
    int y2;
    public DrawingPanel() {
        setBackground(Color.WHITE);
        this.isGraph = false;
        this.y1 = 0;
        this.y2 = 0;
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        g.drawLine(25, 0, 25, 500);
        g.drawLine(50, 0, 50, 500);
        g.drawLine(75, 0, 75, 500);
        g.drawLine(100, 0, 100, 500);
        g.drawLine(125, 0, 125, 500);
        g.drawLine(150, 0, 150, 500);
        g.drawLine(175, 0, 175, 500);
        g.drawLine(200, 0, 200, 500);
        g.drawLine(225, 0, 225, 500);
        g.drawLine(249, 0, 249, 500);
        g.drawLine(250, 0, 250, 500);
        g.drawLine(251, 0, 251, 500);
        g.drawLine(275, 0, 275, 500);
        g.drawLine(300, 0, 300, 500);
        g.drawLine(325, 0, 325, 500);
        g.drawLine(350, 0, 350, 500);
        g.drawLine(375, 0, 375, 500);
        g.drawLine(400, 0, 400, 500);
        g.drawLine(425, 0, 425, 500);
        g.drawLine(450, 0, 450, 500);
        g.drawLine(475, 0, 475, 500);

        g.drawLine(0, 25, 500, 25);
        g.drawLine(0, 50, 500, 50);
        g.drawLine(0, 75, 500, 75);
        g.drawLine(0, 100, 500, 100);
        g.drawLine(0, 125, 500, 125);
        g.drawLine(0, 150, 500, 150);
        g.drawLine(0, 175, 500, 175);
        g.drawLine(0, 200, 500, 200);
        g.drawLine(0, 225, 500, 225);
        g.drawLine(0, 249, 500, 249);
        g.drawLine(0, 250, 500, 250);
        g.drawLine(0, 251, 500, 251);
        g.drawLine(0, 275, 500, 275);
        g.drawLine(0, 300, 500, 300);
        g.drawLine(0, 325, 500, 325);
        g.drawLine(0, 350, 500, 350);
        g.drawLine(0, 375, 500, 375);
        g.drawLine(0, 400, 500, 400);
        g.drawLine(0, 425, 500, 425);
        g.drawLine(0, 450, 500, 450);
        g.drawLine(0, 475, 500, 475);
        if(isGraph){
            g.setColor(Color.BLUE);
            g.drawLine(1, (y1 * 25) + 250, 500, (y2 * 25) + 250);
        }
    }

    public void rep()
    {
        repaint();
    }

    public void setIsGraph(boolean isGraph)
    {
        this.isGraph = isGraph;
    }

    public void setYs(int y1, int y2)
    {
        this.y1 = y1;
        this.y2 = y2;
    }
}
