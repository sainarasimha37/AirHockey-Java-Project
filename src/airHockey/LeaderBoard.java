package airHockey;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderBoard extends JPanel {

    private JTextArea scoreArea;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public LeaderBoard() {
//        setLayout(new BorderLayout());
//        setPreferredSize(new Dimension(World.GAMEWIDTH / 2, World.FRAMEHEIGHT / 2));
//
//        scoreArea = new JTextArea("Leaderboard:\n");
//        scoreArea.setEditable(false);
//        scoreArea.setLineWrap(true);
//        JScrollPane pane = new JScrollPane(scoreArea);
//        add(pane, BorderLayout.CENTER);
//
//        updateScores();
    	setLayout(new BorderLayout());
        setPreferredSize(new Dimension(World.GAMEWIDTH / 2, World.FRAMEHEIGHT / 2));

        scoreArea = new JTextArea("Leaderboard:\n");
        scoreArea.setEditable(false);
        scoreArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(scoreArea);
        add(pane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh Scores");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateScores();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);

        updateScores();
    }

    public void addScore(String player, int score) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
                    "root","");

            // Execute a query to update or insert the score
            stmt = conn.prepareStatement("INSERT INTO leaderboard (player_name, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = GREATEST(score, VALUES(score))");
            stmt.setString(1, player);
            stmt.setInt(2, score);
            stmt.executeUpdate();

            updateScores();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // Clean-up environment
            try {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
        }
    }

    public void updateScores() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false",
                    "root","");

            // Execute a query to get the scores
            stmt = conn.prepareStatement("SELECT * FROM leaderboard ORDER BY score DESC");
            ResultSet rs = stmt.executeQuery();

            StringBuilder scoreStr = new StringBuilder("Leaderboard:\n");
            while(rs.next()) {
                // Retrieve by column name
                String player = rs.getString("player_name");
                int score = rs.getInt("score");
                scoreStr.append(player).append(": ").append(score).append("\n");
            }
            scoreArea.setText(scoreStr.toString());

            rs.close();
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // Clean-up environment
            try {
                if(stmt != null)
                    stmt.close();
                if(conn != null)
                    conn.close();
            } catch(SQLException se2) {
                se2.printStackTrace();
            }
        }
    }
}
