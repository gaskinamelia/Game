import java.awt.*;
import java.awt.Color.*;
import javax.swing.*;
import javax.swing.JPanel.*;
import java.awt.event.*;
/**
 * Chasing_bombs_anlg2 is a game where a player needs to avoid finding the hidden bomb. The player will select the level of difficulty 
 * they wish, from easy, intermediate and difficult. Once selected the player will then 'play' the game where they will select panels 
 * that may or may not be hiding the bomb. If the panel is 'safe' (the bomb is not hidden in that position) the panel will change to yellow to 
 * indicate it's a safe panel and to show the user that they've already tried that panel. Depending on the difficulty level they selected the player
 * can win after so many tries, for example if the player selected 'easy' after selecting five 'safe' panels the player has won. During the game 
 * or after the player wins or loses they can opt to play again or exit. The player cannot select any panels until they have selected a 
 * difficulty level.
 *
 * @author Amelia
 * @version (3.3.2019) v4
 */
public class Game extends JFrame implements ActionListener, MouseListener
{
    private final int ROWS = 2;
    private final int COLUMNS = 5;
    private final int SIZE = ROWS * COLUMNS;
    private final int SPACES = 2;
    private JPanel panelA;
    private JPanel [] panelAA; //panel of individual panels inside panel A
    private JPanel bomb;
    private JPanel panelB;
    private JPanel panelC;
    private JLabel msg;    
    private JButton play, exit, easy, inter, hard;
    private int tries;
    private int points;
    //private int i;
    private boolean lost;
    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        super("chasing-bombs-anlg2");
        setSize(500,300);
        panelAA = new JPanel[SIZE];
        tries = 0;
        points = 0;
        lost = false;
        msg = new JLabel("");
        makeFrame();
        setVisible(true);
    }

    public void makeFrame()
    {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());

        //Creating the panel on the far left of the content pane
        panelA = new JPanel(new GridLayout(ROWS, COLUMNS, SPACES, SPACES));
        add(panelA);
        setBoard();

        //Creating the panel in the center of the content pane
        panelB = new JPanel(new FlowLayout(FlowLayout.CENTER));
        play = new JButton(" Play A Game ");
        play.addActionListener(this);
        exit = new JButton(" Exit ");
        exit.addActionListener(this);
        panelB.add(play);
        panelB.add(exit);
        panelB.setBackground(Color.BLUE);
        panelB.add(msg, BorderLayout.AFTER_LINE_ENDS);
        add(panelB);

        //Creating the panel on the far right of the content pane
        panelC = new JPanel(new FlowLayout(FlowLayout.CENTER));
        easy = new JButton("Easy");
        easy.addActionListener(this);
        inter = new JButton("Intermediate");
        inter.addActionListener(this);
        hard = new JButton("Difficult");
        hard.addActionListener(this);
        panelC.add(easy);
        panelC.add(inter);
        panelC.add(hard);
        panelC.setBackground(Color.GREEN);
        add(panelC);
    }

    /**
     * Setting the colors of the board in panel A
     * if index is on the 3rd panel then change that panel to be the bomb panel 
     */
    public void setBoard()
    {
        for(int i = 0; i < SIZE; i++) {
            if(i == 3){
                bomb = new JPanel();
                panelA.add(bomb);
                bomb.setBackground(Color.RED);
                bomb.addMouseListener(this);
            } else{
                panelAA[i] = new JPanel();
                panelA.add(panelAA[i]);
                panelAA[i].addMouseListener(this);
                panelAA[i].setBackground(Color.RED);
            }
        }
    }

    /**
     * If user selects play, then set points and tries to 0, msg to nothing
     * and lost to false. Change all the panels in panelA to original color if they are yellow. 
     * If user selects exit then close the program.
     * If user selects easy then set the tries to 5.
     * If the user selects intermediate then set the tries to 7.
     * If the user selects difficult then set the tries to 10.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source.equals(play)){
            points = 0;
            lost = false;
            tries = 0; 
            for(int x = 0; x < SIZE; x++){
                if(x == 3){
                    if(bomb.getBackground() == Color.RED){
                        bomb.setBackground(Color.RED);
                    }                
                } else if(panelAA[x].getBackground() == Color.YELLOW){
                    panelAA[x].setBackground(Color.RED);                
                }
            }
            msg.setText("");
        } else if(source.equals(exit)){
            System.exit(0);
        } else if(source.equals(easy)){
            tries = 5;
        } else if(source.equals(inter)){
            tries = 7;
        }else if(source.equals(hard)){
            tries = 10;
        }

    }

    /**
     * if the player hasn't lost and the number of tries(safe panels) is greater than 0 the player is able to select the panels. If the panel 
     * they select is not hiding the bomb then that panel color changes to yellow and points is incremented. If points are greater than 0 and 
     * equal the number of tries from selecting a difficulty level then the player wins. However, if the player selects the bomb panel then the 
     * player loses and the number of points they occured throughout the game is printed.
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {  
        JPanel p = (JPanel) e.getSource();
        if(lost == false && tries > 0){
            for(int i = 0; i < SIZE; i++){            
                if(p.equals(panelAA[i])){
                    if(p.getBackground() == Color.RED){
                        p.setBackground(Color.YELLOW);
                        points++;
                    }                    
                }
                if(points == tries){
                    msg.setText("You win! Congratulations");
                    lost = true;
                }
            }
            if(p.equals(bomb)){
                lost = true;
                msg.setText("You lose! You got " + points + " points");
            }
        } 
    }

    @Override 
    public void mousePressed(MouseEvent e){}

    @Override 
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}
}