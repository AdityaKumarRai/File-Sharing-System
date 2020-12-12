import java.awt.*;
import java.awt.event.*;
class clientform extends Frame
{
Label send;
TextArea ta;
clientform(String mess)
{
super("DOTSOFT");
setVisible(true);
setLocation(200,150);
setSize(300,250);
setLayout(new BorderLayout());
for(int i=0;i<3;i++)
System.out.println((char)7);
setBackground(Color.lightGray);
ta=new TextArea();
ta.setFont(new Font("",Font.PLAIN,18));
ta.setText(mess);
ta.disable();
send=new Label("     Message From DotSoft");
send.setForeground(Color.red);
send.setFont(new Font("",Font.BOLD,22));
add(ta,BorderLayout.CENTER);
add(send,BorderLayout.NORTH);
add(new Label(" "),BorderLayout.SOUTH);
addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){setVisible(false);}});
}
/*public static void main(String ds[])
{
new clientform("subrahmanyam");
} */
}


