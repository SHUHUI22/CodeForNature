/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package codefornature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class History extends javax.swing.JFrame {
    private Trivia TriviaFrameReference;
    private int points;
    private String username;
    private int day;
    
    /**
     * Creates new form History
     */
    public History(Trivia TriviaFrameReference,int points,String username,int day) {
        this.TriviaFrameReference=TriviaFrameReference;
        this.points=points;
        this.username=username;
        this.day=day;
        initComponents();
        displayNoAttemptQuestion();
        setupBtn();
        
    }
    
    public void displayNoAttemptQuestion(){
        String SUrl,SUser,SPass,query;
        int attempt=0;
        ArrayList<Integer> allQs=new ArrayList<>();
        ArrayList<Integer> RepeatableQs=new ArrayList<>();
        boolean correct=false;
        
        SUrl="jdbc:mysql://localhost:3306/user";
        SUser="root";
        SPass="";
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection(SUrl,SUser,SPass);
                Statement st=con.createStatement();
                query="SELECT * FROM attempt WHERE username='"+username+"'";
                ResultSet rs=st.executeQuery(query);
                while(rs.next()){
                    allQs.add(rs.getInt("day"));
                }
                for(int i=1;i<=10;i++){
                    if(!allQs.contains(i)){
                        RepeatableQs.add(i);
                    }
                    else{
                        query = "SELECT * FROM attempt WHERE username='" + username + "' AND day=" + i;
                        rs = st.executeQuery(query);
                        if (rs.next()) {
                            correct = rs.getBoolean("correct");
                            attempt = rs.getInt("attempt");
                            if (!correct && attempt == 1) {
                                RepeatableQs.add(i);
                              
                            }
                        }
                    }
                }
                StringBuilder sb=new StringBuilder();
                for(int i:RepeatableQs){
                    sb.append(i+"   ");
                    
                }
                String stringRepeatableQs=sb.toString();
                fQs.setText(stringRepeatableQs);
            
            }
            catch(Exception e){
                System.out.println(e.getMessage()); 
            }
    }
    
    public void setupBtn(){
        goBtn.addActionListener (e -> {
            String SUrl,SUser,SPass,query;
            int question=0,attempt=0;
            boolean found=false,correct=false;
            SUrl="jdbc:mysql://localhost:3306/user";
            SUser="root";
            SPass="";
            try{
                if("".equals(fQuestion.getText())){
                    JOptionPane.showMessageDialog(new JFrame(),"No. of question is required","Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(SUrl,SUser,SPass);
                    Statement st=con.createStatement();
                    question=Integer.parseInt(fQuestion.getText());
                    if(question<=day&&question<=10){
                        found=true;
                    }
                    query="SELECT * FROM attempt WHERE username='"+username+"' AND day='"+question+"'"; 
                    ResultSet rs=st.executeQuery(query);
                    while(rs.next()){
                        correct=rs.getBoolean("correct");
                        attempt=rs.getInt("attempt");
                        break;
                    }
                }
            }
            catch(Exception i){
                System.out.println(i.getMessage());
                return;
            }
            if(found){
                if(attempt>2||correct==true){
                    TriviaFrameReference.passQ(question);
                    fQs.setVisible(false);
                    this.dispose();
                    }
                if(attempt==0&&correct==false||(attempt==1&&correct==false)){
                    TriviaFrameReference.page(question);
                    this.dispose();
                }    
            }
            else{
               JOptionPane.showMessageDialog(new JFrame(),"You cannot access this question yet.","Error",JOptionPane.ERROR_MESSAGE); 
               fQuestion.setText("");
            }
        }); 
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backBtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fShow = new javax.swing.JLabel();
        fQuestion = new javax.swing.JTextField();
        clearBtn = new javax.swing.JButton();
        goBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        fQs = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(986, 750));
        setMinimumSize(new java.awt.Dimension(986, 750));
        setPreferredSize(new java.awt.Dimension(986, 750));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backBtn.setBackground(new java.awt.Color(0, 102, 102));
        backBtn.setIcon(new javax.swing.ImageIcon("D:\\UM\\Y1\\S1\\FOP\\CodeForNature\\src\\icon\\back.jpg")); // NOI18N
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 100));

        jLabel9.setFont(new java.awt.Font("Showcard Gothic", 1, 60)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("HISTORY");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("Enter no. of question: ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 174, -1, 62));

        fShow.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        fShow.setForeground(new java.awt.Color(0, 51, 51));
        fShow.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 51), new java.awt.Color(0, 51, 51), new java.awt.Color(0, 51, 51), new java.awt.Color(0, 51, 51)));
        getContentPane().add(fShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 880, 286));

        fQuestion.setBackground(new java.awt.Color(204, 255, 204));
        fQuestion.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        fQuestion.setForeground(new java.awt.Color(0, 51, 51));
        getContentPane().add(fQuestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 247, 62));

        clearBtn.setBackground(new java.awt.Color(204, 255, 204));
        clearBtn.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(0, 51, 51));
        clearBtn.setText("CLEAR");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });
        getContentPane().add(clearBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 590, 180, 70));

        goBtn.setBackground(new java.awt.Color(204, 255, 204));
        goBtn.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        goBtn.setForeground(new java.awt.Color(0, 51, 51));
        goBtn.setText("GO");
        getContentPane().add(goBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 590, 180, 70));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("No. of question that can be attempted: ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, 350, 50));

        fQs.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        fQs.setForeground(new java.awt.Color(0, 51, 51));
        fQs.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 51), new java.awt.Color(0, 51, 51)));
        getContentPane().add(fQs, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 340, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon("D:\\UM\\Y1\\S1\\FOP\\CodeForNature\\src\\icon\\trivia bg2.jpg")); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, -30, 1130, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        this.TriviaFrameReference.setVisible(true);
        TriviaFrameReference.pack();
        TriviaFrameReference.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        this.dispose();
        fShow.setText("");
        fQuestion.setText("");
        TriviaFrameReference.historyBtnActionPerformed(evt);
        setupBtn();
    }//GEN-LAST:event_clearBtnActionPerformed

    public void display(ArrayList<String> data,int num){
        fQuestion.setText(Integer.toString(num));
        fShow.setText("<html><div style='width:600px;'>"+"Question "+num+": "+data.get(0)+"<br>"
                +"<br>"
                +"A. "+data.get(1)+"<br>" 
                +"B. "+data.get(2)+"<br>"
                +"C. "+data.get(3)+"<br>"
                +"D. "+data.get(4)+"<br>"
                +"<br>"
                +"Correct Answer: "+data.get(5)+"</div></html>");
    }
    
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JLabel fQs;
    private javax.swing.JTextField fQuestion;
    private javax.swing.JLabel fShow;
    private javax.swing.JButton goBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
