package curriculum_design;

import java.awt.BorderLayout;					
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//以下是主类CalendarPad
public class CalendarPad extends JFrame implements MouseListener{
	
   int year,month,day;
   Hashtable hashtable;             
   File file;                       
   static JTextField showDay[];             
   JLabel title[];                   
   Calendar calendar;
   int week; 
   NotePad notepad=null;             
   Month changemonth;
   Year  changeyear;
   String 星期[]={"日","一","二","三","四","五","六"};
   JPanel leftPanel,rightPanel;    
 
   //以下是定义CalendarPad方法
   public  CalendarPad(int year,int month,int day)
   { 
	  super("Duang日历记事本");				//设置窗体标题
     leftPanel=new JPanel();
     JPanel leftCenter=new JPanel();
     JPanel leftNorth=new JPanel();
     leftCenter.setLayout(new GridLayout(7,7));   
                                                  
     rightPanel=new JPanel();
     this.year=year;
     this.month=month;
     this.day=day;
     changeyear=new Year(this);
     changeyear.setYear(year);
     changemonth=new Month(this);
     changemonth.setMonth(month);
  
     title=new JLabel[7];                         //定义显示星期标签
     showDay=new JTextField[42];                   
     for(int j=0;j<7;j++)                         
       {
         title[j]=new JLabel();
         title[j].setText(星期[j]);				//定义显示星期标签
         title[j].setBorder(BorderFactory.createRaisedBevelBorder());				//定义边框为斜面边框（凸）
         leftCenter.add(title[j]);				//显示星期标签
       } 
     title[0].setForeground(Color.red);				//将周日显示为红色
     title[6].setForeground(Color.red);				//将周六显示为红色

     for(int i=0;i<42;i++)                        
       {
         showDay[i]=new JTextField();
         showDay[i].addMouseListener(this);			//添加鼠标进入
         showDay[i].setEditable(false);				//设置为不可编辑标签
         leftCenter.add(showDay[i]);
       }
         
     calendar=Calendar.getInstance();
     Box box=Box.createHorizontalBox();          
     box.add(changeyear);				//添加改变年控件
     box.add(changemonth);				//添加改变月控件
     leftNorth.add(box);					//定位改变年月标签
     leftPanel.setLayout(new BorderLayout());
     leftPanel.add(leftNorth,BorderLayout.NORTH);
     leftPanel.add(leftCenter,BorderLayout.CENTER);
     leftPanel.add(new Label("201403041020   XXX   201403041050   XXX"),
                  BorderLayout.SOUTH) ;
     leftPanel.validate();
     Container con=getContentPane();
     JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                     leftPanel,rightPanel);
     
     con.add(split,BorderLayout.CENTER);
     con.validate();
    
     hashtable=new Hashtable();
     file=new File("日历记事本.txt");				//新建"日历记事本.txt"文件
      if(!file.exists())						//判断文件是否存在
      {
       try{
           FileOutputStream out=new FileOutputStream(file);
           ObjectOutputStream objectOut=new ObjectOutputStream(out);
           objectOut.writeObject(hashtable);
           objectOut.close();
           out.close();
          }
       catch(IOException e)
          {
          }
      } 
    
     notepad=new NotePad(this);                                      
     rightPanel.add(notepad);
     set_calendar(year,month);
     addWindowListener(new WindowAdapter()				//注册监听器
                    { public void windowClosing(WindowEvent e)
                       {
                         System.exit(0);
                       }
                    });
    setVisible(true);				//设置主面板可见
    setBounds(500,100,600,285);				//设置主面板大小及位置
    validate();
   }
   
   /*以下为set_calendar方法
    * 实现不同月份的天数显示
    */
  public void set_calendar(int year,int month)
   {
     calendar.set(year,month-1,1);              
     
     week=calendar.get(Calendar.DAY_OF_WEEK)-1;
     if(month==1||month==2||month==3||month==5||month==7
                        ||month==8||month==10||month==12)
        {   arraynum(week,31);         
        }
     else if(month==4||month==6||month==9||month==11)
        {  arraynum(week,30);
        }
     else if(month==2)
        {   if((year%4==0&&year%100!=0)||(year%400==0))  
           {   arraynum(week,29);
           }
         else
           {   arraynum(week,28);
           }
       }
   } 
 
  /*以下是arraynum方法
   * 显示日历表格标签中的颜色显示
   */
 public void arraynum(int week,int month)
   {
      for(int i=week,n=1;i<week+month;i++)
             {   showDay[i].setText(""+n);
           
               if(n==day)
                 {  
            	   showDay[i].setForeground(Color.black); 
            	   showDay[i].setBackground(Color.blue);
                   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,20));
                 }
               else
                 {   
            	   showDay[i].setFont(new Font("TimesRoman",Font.BOLD,12));
                   showDay[i].setBackground(Color.white);
                   showDay[i].setForeground(Color.black);
                 }
               if(i%7==6)				//设置日期为周六显示为红色
                 {   showDay[i].setForeground(Color.red);  
                 }
               if(i%7==0)				//设置日期为周日显示为红色
                 {  
            	   showDay[i].setForeground(Color.red);  
                 }
               n++; 
             }
       for(int i=0;i<week;i++)
             {  showDay[i].setText("");
             }
       for(int i=week+month;i<42;i++)
             {   
    	   showDay[i].setText("");
             }
   }

 //定义getYear，setYear，getMonth，setMonth，getDay，setDay，getHashtable，getFile多个方法
 public int getYear()				
   {   
	 return year;
   }
 
 public void setYear(int y)
   {   
	 year=y;
     notepad.setYear(year);
   }
 
 public int getMonth()
   {  
	 return month;
   }
 
 public void setMonth(int m)
   {  
	 month=m;
      notepad.setMonth(month); 
   }
 
 public int getDay()
   {  
	 return day;
   }
 
 public void setDay(int d)
   {   
	 day=d;
       notepad.setDay(day);
   }
 
 public Hashtable getHashtable()
   {   
	 return hashtable;
   }
 
 public File getFile()
   {  
	 return file;
   }
 
 /*以下是mouseClicked方法（非 Javadoc）
  * 设置多个标签
  * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
  */
  public void mouseClicked(MouseEvent e)
   {
	 JTextField source=(JTextField)e.getSource();
     try{
        day=Integer.parseInt(source.getText());
        notepad.setDay(day);
        notepad.setIFLine(year,month,day);
        notepad.setText(null);
        notepad.lookCalendar(year,month,day);
       } 
     catch(Exception ee)
     {
     }
   }
 
 //定义多个鼠标点击事件

 public void mousePressed(MouseEvent e)             
   { 
   }

 public void mouseReleased(MouseEvent e)
   {
   }
 
 public void mouseEntered(MouseEvent e)
   {
   }
 
 public void mouseExited(MouseEvent e)
   {
   }
 
/*定义NotePad类
 * 实现对记事本面板的属性设置
 * 新建多个按钮和标签以便完成对记事本的保存和删除以及对新建的记事本的保存
 */
class NotePad extends JPanel implements ActionListener
{ 
	JTextArea text;              
	JButton save_text,rm_text;
    Hashtable table;             
    JLabel IFLine;               
    int year,month,day;          
    File file;                   
    CalendarPad calendar;
    
    public  NotePad(CalendarPad calendar)
   {
     this.calendar=calendar;
     year=calendar.getYear();
     month=calendar.getMonth();
     day=calendar.getDay();
     table=calendar.getHashtable();
     file=calendar.getFile();
     IFLine=new JLabel(""+year+"年"+month+"月"+day+"日",JLabel.CENTER);
     IFLine.setFont(new Font("TimesRoman",Font.BOLD,16));
     IFLine.setForeground(Color.blue);
     text=new JTextArea(5,10);				//设置文本空间的大小
     save_text=new JButton("保存日志") ;				//新建“保存日志”按钮
     rm_text=new JButton("删除日志") ;					//新建“删除日志”按钮
     save_text.addActionListener(this);					//注册监听器
     rm_text.addActionListener(this);						
     setLayout(new BorderLayout());
     JPanel pSouth=new JPanel();       
     add(IFLine,BorderLayout.NORTH);
     pSouth.add(save_text);
     pSouth.add(rm_text);
     add(pSouth,BorderLayout.SOUTH);
     add(new JScrollPane(text),BorderLayout.CENTER);
   }
    
    /*以下是actionPerformed方法
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 获得点击“保存日志”和“删除日志”两个按钮的事件
     */
 public void actionPerformed(ActionEvent e)
   {
     if(e.getSource()==save_text)
      {   
    	 save_text(year,month,day);
      }
     else
    	 if(e.getSource()==rm_text)
      { 
    		 rm_text(year,month,day);
      }
   }
 
  public void setYear(int year)
  {   
	  this.year=year;
  }
  
 public int getYear()
  {    
	 return year;
  }
 
 public void setMonth(int month)
  {   
	 this.month=month;
  } 
 
  public int getMonth()
  {  
	  return month;
  } 
  
  public void setDay(int day)
  {   
	  this.day=day;
  }
  
  public int getDay()
   {   
	  return day;
   }
  
  /*以下是setIFLine方法
   * 设定信息条显示选中的日期
   */
 public void setIFLine(int year,int month,int day)
   {  
	 IFLine.setText(""+year+"年"+month+"月"+day+"日");
   }
 
  public void setText(String s)
   {  
	  text.setText(s);
   }
  
  /*以下是lookCalendar方法
   * 通过FileInputStream和ObjectInputStream对输入的文本进行持久存储
   * ObjectInputStream用于恢复以前序列化的对象
   */
  public void lookCalendar(int year,int month,int day)
   {  
	  String key=""+year+""+month+""+day;
       try
             {
              FileInputStream   inOne=new FileInputStream(file);
              ObjectInputStream inTwo=new ObjectInputStream(inOne);
              table=(Hashtable)inTwo.readObject();         
              inOne.close();
              inTwo.close();
             }
       catch(Exception ee)
             {
             }
       
       /*以下是判断当前日期是否有记事文本的判断语句
        * 通过containsKey验证当前日期是否有记事文本
        * 如果有则提示"这一天有日志记载，是否查看?"并通过点击按钮选择是否查看
        * 如果没有文本，则在记事本中直接显示无记录
        */
       if(table.containsKey(key))
          {  String m=""+year+"年"+month+"月"+day+"这一天有日志记载，是否查看?";
             int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
                if(ok==JOptionPane.YES_OPTION)
                  {  text.setText((String)table.get(key));
                  }
                else
                  {   text.setText(""); 
                  }
          } 
       else
          {   text.setText("无记录");
          } 
   }
 
  /*以下是save_text方法
   * 点击“保存日志”后通过showConfirmDialog方法会弹出一个询问确认问题
   * 通过点击两个按钮实现保存和不保存文本
   */
  public void save_text(int year,int month,int day)
   {    
	  String Calendar_text=text.getText();
        String key=""+year+""+month+""+day;
        String m=""+year+"年"+month+"月"+day+"是否保存日志?";
        int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
        if(ok==JOptionPane.YES_OPTION)
          {
           try
             {
              FileInputStream   inOne=new FileInputStream(file);
              ObjectInputStream inTwo=new ObjectInputStream(inOne);
              table=(Hashtable)inTwo.readObject();
              inOne.close();
              inTwo.close();
              table.put(key,Calendar_text);                                  
              FileOutputStream out=new FileOutputStream(file);
              ObjectOutputStream objectOut=new ObjectOutputStream(out);
              objectOut.writeObject(table);
              objectOut.close();
              out.close();
             }
           catch(Exception ee)
             {
             }
         }
   }
  
  /*以下是rm_text方法
   *  点击“删除日志”后通过showConfirmDialog方法会弹出一个询问确认问题
   *  通过点击两个按钮来实现删除和不删除文本
   */
  public void rm_text(int year,int month,int day)
   {   
	  String key=""+year+""+month+""+day;
         if(table.containsKey(key))
          {    
        	 String m="删除"+year+"年"+month+"月"+day+"日的日志吗?";
             int ok=JOptionPane.showConfirmDialog(this,m,"询问",JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
              if(ok==JOptionPane.YES_OPTION)
              { 
              try
                {
                 FileInputStream   inOne=new FileInputStream(file);				
                 ObjectInputStream inTwo=new ObjectInputStream(inOne);
                 table=(Hashtable)inTwo.readObject();
                 inOne.close();
                 inTwo.close();
                 table.remove(key);                                        
                 FileOutputStream out=new FileOutputStream(file);
                 ObjectOutputStream objectOut=new ObjectOutputStream(out);
                 objectOut.writeObject(table);
                 objectOut.close();
                 out.close();
                 text.setText(null);
                }
               catch(Exception ee)
                {
                }
              }
          }
        else
          {  
        	String m=""+year+"年"+month+"月"+day+"无日志记录";
            JOptionPane.showMessageDialog(this,m,"提示",JOptionPane.WARNING_MESSAGE);
          }
   }
}

/*定义Year类
 * 新建年月的标签并设置“上年”和“下年”的属性
 * 通过actionPerformed方法获得对按钮的点击事件并作出相应显示效果改变
 */
class Year extends Box implements ActionListener
{  
	int year;                           
    JTextField showYear=null;           
    JButton nextyear,lastyear;
    CalendarPad calendar_2;
    
    /*以下是Year方法
     * 新建文本标签并设置相关属性
     * 新建两个调整“年”的按钮
     */
    public Year(CalendarPad calendar_2)
    {  
    	super(BoxLayout.X_AXIS);        
        showYear=new JTextField(4);				//新建文本标签
        showYear.setForeground(Color.blue);				//设置文本标签
        showYear.setFont(new Font("TimesRomn",Font.BOLD,14)); 				
        this.calendar_2=calendar_2;
        year=calendar_2.getYear();
        nextyear=new JButton("下年");				//新建按钮
        lastyear=new JButton("上年");
        add(lastyear);						//添加标签和按钮
        add(showYear);
        add(nextyear);
        showYear.addActionListener(this);				//注册监视器
        lastyear.addActionListener(this);
        nextyear.addActionListener(this);
  }
    
 public void setYear(int year)
  {  this.year=year;
     showYear.setText(""+year);
  }
 
 public int getYear()
  { 
	 return year;
  } 

/*初始化方法actionPerformed
 * 获得点击“上年”和“下年”按钮动作
 * 改变面板中显示结果
 *  （非 Javadoc）
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
 public void actionPerformed(ActionEvent e)
  {  
	 if(e.getSource()==lastyear)
      {  
		 year=year-1;
         showYear.setText(""+year);
         calendar_2.setYear(year);
         calendar_2.set_calendar(year,calendar_2.getMonth());
      }
    else 
    	if(e.getSource()==nextyear)
      { 
    		year=year+1;
    		showYear.setText(""+year);
    		calendar_2.setYear(year);
    		calendar_2.set_calendar(year,calendar_2.getMonth());
      }
    else 
    	if(e.getSource()==showYear)
      { 
    		try
            { 
    			year=Integer.parseInt(showYear.getText());
    			showYear.setText(""+year);
    			calendar_2.setYear(year);
    			calendar_2.set_calendar(year,calendar_2.getMonth());
            }
         catch(NumberFormatException ee)
            { 
        	 showYear.setText(""+year);
             calendar_2.setYear(year);
             calendar_2.set_calendar(year,calendar_2.getMonth());
            }
      }
  }   
}

/*定义Month类
 * 新建“月”面板，添加“上月”和“下月”按钮
 */
 class Month extends Box implements ActionListener
{ 
	 int month;                           
	 JTextField showMonth=null;           
	 JButton nextmonth,lastmonth;
	 CalendarPad calendar_3;
	 
/*以下是Month方法
 *新建标签和按钮
 *实现对“月”的切换并在主面板上显示
 *通过actionPerformed方法获得点击按钮事件
 *改变相应显示效果
 */
	 public Month(CalendarPad calendar_3)
  {  
     super(BoxLayout.X_AXIS);        
     this.calendar_3=calendar_3;
     showMonth=new JTextField(2);
     month=calendar_3.getMonth();
     showMonth.setEditable(false);
     showMonth.setForeground(Color.blue);
     showMonth.setFont(new Font("TimesRomn",Font.BOLD,16)); 				//设置面板中显示效果
     nextmonth=new JButton("下月");				//新建按钮
     lastmonth=new JButton("上月");
     add(lastmonth);								//添加按钮
     add(showMonth);
     add(nextmonth);
     lastmonth.addActionListener(this);
     nextmonth.addActionListener(this);
     showMonth.setText(""+month);
  }
 
 public void setMonth(int month)
  {  
	 if(month<=12&&month>=1)
      {  
	  this.month=month;
      }
    else
      {  
    	this.month=1;
      }
    showMonth.setText(""+month);
  }
 
 public int getMonth()
  {   
	 return month;
  } 
 
 /*初始换actionPerformed方法
  *获得点击按钮动作
  *做出相应月份改变并在面板上显示
  * （非 Javadoc）
  * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
  */
 public void actionPerformed(ActionEvent e)
  {  
	 if(e.getSource()==lastmonth)
      {  
		 if(month>=2)
         {  
			 month=month-1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
         }
        else 
        	if(month==1)
         {  
        		month=12;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
         }
       showMonth.setText(""+month);
      }
    else if(e.getSource()==nextmonth)
      {  if(month<12)
          {  month=month+1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
          }
        else if(month==12)
          {  month=1;
            calendar_3.setMonth(month);
            calendar_3.set_calendar(calendar_3.getYear(),month);
          }
        showMonth.setText(""+month);
      }
  }   
}
 
 //以下是程序的main主方法
 public static void main(String args[])
   {   
	 Calendar calendar=Calendar.getInstance();    
     int y=calendar.get(Calendar.YEAR);           
     int m=calendar.get(Calendar.MONTH)+1;        
     int d=calendar.get(Calendar.DAY_OF_MONTH);
     CalendarPad  calendarpad= new CalendarPad(y,m,d);
   }
}



