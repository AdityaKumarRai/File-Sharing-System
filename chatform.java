import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
class chatform extends JFrame implements Runnable,ActionListener,MouseListener
{
JButton send,exit,kill;
JTextArea ta,ta1;
JTextField tf;
JLabel l1;
JScrollPane jsp,jsp1;
DataInputStream rin;
DataOutputStream out;
int n=0;
String user,mess;
JLabel l[]=new JLabel[100];
JPanel p2;
Vector v=new Vector();
Vector tip=new Vector();
//chat c1;
static int z=0;
byte b[]=new byte[50];
form1 f;
sendall all;
Connection con;
Statement stmt;
ResultSet rs,rs1,rs2;
static int in=0;
chatform()
{
super("CONNECTED USERS INFORMATION");
try
{
Socket cli=new Socket("10.8.1.15",143);
rin=new DataInputStream(cli.getInputStream());
out=new DataOutputStream(cli.getOutputStream());
InetAddress inet=InetAddress.getLocalHost();
user=inet.getHostAddress();
byte x[]=("Theusername, "+user).getBytes();
System.out.println(new String(x));
out.write(x,0,x.length);
Container c=getContentPane();
c.setLayout(new BorderLayout());
show();
setSize(600,425);
setLocation(75,75);
JPanel p=new JPanel(new BorderLayout());
JPanel p1=new JPanel(new FlowLayout());
p2=new JPanel(new GridLayout(100,1,0,10));
ta=new JTextArea();
tf=new JTextField();
ta.setFont(new Font("",Font.BOLD,18));
ta.disable();
int v1=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
jsp=new JScrollPane(ta,v1,h);
p.add(jsp,BorderLayout.CENTER);
p.add(tf,BorderLayout.SOUTH);
send=new JButton("SEND ALL");
exit=new JButton("EXIT");
kill=new JButton("KILL");
p1.add(kill);
p1.add(send);
p1.add(exit);
exit.addActionListener(this);
send.addActionListener(this);
kill.addActionListener(this);
l1=new JLabel("                        ALL CONNECTED USERS");
l1.setFont(new Font("helvatica",Font.BOLD,22));
l1.setForeground(Color.darkGray);
/*ta.addMouseListener(new MouseAdapter()
{
public void mouseEntered(MouseEvent me){
ta.disable();
}
public void mouseExited(MouseEvent me){
ta.enable();}
});*/
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:raj");
stmt=con.createStatement();
rs=stmt.executeQuery("select *from users");
rs2=rs;
rs1=rs;
}
catch(Exception e){System.out.println(e);}
         rin.read(b);
         String str=new String(b).trim();
         System.out.println(str);
         StringTokenizer s=new StringTokenizer(str," ");
        while(s.hasMoreTokens())
        {
        String t=s.nextToken();
        rs1=stmt.executeQuery("select *from users");
        int flag=0,i=0;
        if(!t.equals("allusers"))
        {
        ta.append(t+"  :Presently Connected"+"\n");
        v.addElement(t);
        while(rs1.next())
        {
        i++;
        if(t.trim().equals(rs1.getString(1)))
        {tip.addElement(rs1.getString(2));flag=1;}
        }
        if(flag==0)
        tip.addElement("Not Updated");
        System.out.println("sumitra"+i+" flag"+flag);
        }
        System.out.println(v.size()+" "+tip.size());
        }
        
for(int i=0;i<100;i++)
{
l[i]=new JLabel("");
p2.add(l[i]);
}
jsp1=new JScrollPane(p2,v1,h);

l1.updateUI();
p.updateUI();
p1.updateUI();
jsp1.updateUI();

c.add(l1,BorderLayout.NORTH);
c.add(p,BorderLayout.CENTER);
c.add(p1,BorderLayout.SOUTH);
c.add(jsp1,BorderLayout.EAST);
addlabel();
Thread t=new Thread(this);
t.start();
}catch(Exception e){System.out.println("anand1 "+e);}
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==exit)
{
System.exit(0);
}
if(ae.getSource()==send)
{
Vector ve=new Vector(v);
in++;
all=new sendall(this,user,ve);
}
if(ae.getSource()==kill)
{
Vector ve=new Vector(v);
new killall(this,ve);
}
}
public void run()
{
String msg;
try
{
while(true)
{
byte b[]=new byte[200];
        if(rin.read(b)>=1)
        {
        msg=new String(b).trim();
//        System.out.println("Comming message :"+msg);
        if(msg.endsWith("allusers"))
        {
        StringTokenizer s=new StringTokenizer(msg," ");
        int x=s.countTokens();
        for(int i=1;i<x-1;i++)
        s.nextToken();
        String u=s.nextToken();
        ta.append(u+"  :Recently Connected"+"\n");
        v.addElement(u);
        rs2=stmt.executeQuery("select *from users");
        int flag=0;
        while(rs2.next())
        {
        if(u.trim().equals(rs2.getString(1)))
        {tip.addElement(rs2.getString(2));flag=1;}
        }
        if(flag==0)
        tip.addElement("Not Updated");
        System.out.println(v.size()+" "+tip.size());
        addlabel();
        }
        else
        if(msg.endsWith("exited123"))
        {
        System.out.println(msg);
        StringTokenizer s=new StringTokenizer(msg," ");
        String t=s.nextToken();
        ta.append(t+"  : Disconnected"+"\n");
        int x1=v.indexOf(t);
        v.removeElement(t);
        tip.removeElementAt(x1);
        System.out.println(v.size()+" "+tip.size());
        addlabel();
        }
        if(msg.indexOf("sizel")>=1)
        {
        int flag=0;
        System.out.println(msg);
        StringTokenizer str=new StringTokenizer(msg," ");
        String sender=str.nextToken();
        str.nextToken();
        int size=Integer.parseInt(str.nextToken());
        byte b1[]=new byte[size];
        try
        {
        String pat=str.nextToken();
        System.out.println("The dest Path "+pat);
        FileOutputStream fout=new FileOutputStream(pat);
        out.flush();
        rin.readFully(b1,0,size);
        fout.write(b1);
        System.out.println("File Captured");
        out.write(("captured "+" "+user+" "+sender).getBytes());
        //     System.out.println(new String(b1));
        }catch(Exception e){System.out.println(e);flag=1;}

        if(flag==1)
        {
        out.flush();
        out.write(("nopath "+" "+user+" "+sender).getBytes());
        }
        }
        if(msg.startsWith("captured"))
        {
        System.out.println("Entered in captured");
        System.out.println(msg);
        StringTokenizer str=new StringTokenizer(msg," ");
        str.nextToken();
        str.nextToken();
        String s=str.nextToken();
        //System.out.println(f.status2.getText());
        if(in==0)
        f.status2.append("File Transfered To System: "+s+"\n");
        else
        all.f.status2.append("File Transfered To System: "+s+"\n");
        }
        if(msg.startsWith("nopath"))
        {
        System.out.println("Entered in nopath");
        System.out.println(msg);
        StringTokenizer str=new StringTokenizer(msg," ");
        str.nextToken();
        str.nextToken();
        String s=str.nextToken();
        //System.out.println(f.status2.getText());
        if(in==0)
        f.status2.append("Path Not Found In System: "+s+"\n");
        else
        all.f.status2.append("Path Not Found In System: "+s+"\n");
        }
        if(msg.endsWith("mustquit"))
        {
        System.out.println("You have been Forcibly removed from server");
        System.exit(0);
        }
        }
}
}catch(Exception e){System.out.println("anand2 "+e);}
}
void addlabel()
{
System.out.println("The size is"+v.size());
for(int i=0;i<v.size();i++)
{
String t="   "+(String)v.elementAt(i)+"   ";
System.out.println(t);
l[i].setFont(new Font("",Font.BOLD,16));
l[i].setText(t);
l[i].setToolTipText((String)tip.elementAt(i));
if(user.equals((String)v.elementAt(i)))
{l[i].setForeground(Color.red);
continue;}

l[i].setForeground(Color.blue);
l[i].addMouseListener(this);
}
for(int j=v.size();j<10;j++)
{l[j].setText("");l[j].removeMouseListener(this);l[j].setToolTipText("");}
}
public void mousePressed(MouseEvent me){}
public void mouseEntered(MouseEvent me){setCursor(Cursor.HAND_CURSOR);}
public void mouseExited(MouseEvent me){setCursor(Cursor.DEFAULT_CURSOR);}
public void mouseClicked(MouseEvent me){
if(me.getClickCount()==1)
{
JLabel l=(JLabel)me.getComponent();
in=0;
f=new form(this,l.getText(),user,null);
}

}
public void mouseReleased(MouseEvent me){}
public static void main(String ds[])
{
new chatform();
}
}

