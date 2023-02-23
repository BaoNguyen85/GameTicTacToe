package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.util.Calendar;

//		00|10|20
//		01|11|21
//		02|12|22

public class TicTacToe extends JFrame {
	int movesLeft;                            		//di chuyển tìm ô trống
	int Xgoal=0;									//điểm của X
	int Ogoal=0;									//điểm của Y
	boolean gameNotOver;                      		//đúng miễn là trò chơi chưa kết thúc
	boolean onePlayer;                        		//đúng nếu là chế độ chơi đơn
	String winPlayer,currentPlayer,computerPlayer;  //theo dõi chiến thắng, người chơi hiện tại và máy tính
	JLabel title;
	JButton modeOne;                          		//nút để chọn chế độ chơi đơn
	JButton modeTwo;                          		//nút để chọn chế độ hai người chơi
	JButton[][] buttons;                      		//mảng các nút trên bảng
	JLabel status;                            		//hiển thị trạng thái của trò chơi hiện tại								
	JPanel board;                             		//chứa các nút
	JPanel mode;                              		//chứa các tùy chọn chế độ 
	JButton exit;									//nút thoát
	JButton reset;									//nút reset
	JButton start;									//nút bắt đầu tính điểm
	JButton help;									//nút hiển thị thông tin trò chơi
	JPanel Pane;									//các các nút chức năng trong ván đấu
	JPanel st, ifor;
	JLabel ifo;
	JLabel time, timeWait;							//nhãn thời gian và thời gian chờ
	JPanel TimePanel;								//chứa các label thời gian và thời gian chờ
	Timer timer;									//lớp timer cho thời gian
	int second, minute, wait;						//các giá trị giây, phút, thời gian chờ
	
	public TicTacToe() {
		TimePanel = new JPanel();
		time = new JLabel("",SwingConstants.LEFT);	
		time.setForeground(new Color(185, 211, 238));
		timeWait = new JLabel("",SwingConstants.LEFT);	
		timeWait.setForeground(new Color(188, 143, 143));
		second=0;
		minute=0;
		wait=30;
		timer();
		TimePanel.add(time);
		TimePanel.add(timeWait);
		TimePanel.setForeground(new Color(185, 211, 238));
		TimePanel.setBackground(new Color(16, 78, 139));
		
		ifor = new JPanel();
		ifo = new JLabel("Nguyen Chi Bao - B1906430", SwingConstants.CENTER);
		ifo.setFont(new Font("Segoe Script", Font.BOLD, 28));
		ifo.setForeground(new Color(185, 211, 238));
		
		st = new JPanel();
		status = new JLabel("Game Tic Tac Toe", SwingConstants.CENTER);
		status.setFont(new Font("Segoe Script", Font.BOLD, 18));
		status.setForeground(new Color(185, 211, 238));
		
		title = new JLabel("CHOOSE A MODE", SwingConstants.CENTER);
		title.setFont(new Font("Arial Narrow", Font.BOLD, 35));
		mode = new JPanel();
		mode.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 100));
		modeOne = new JButton("One Player");
		modeTwo = new JButton("Two Players");
		mode.add(title);
		title.setPreferredSize(new Dimension(500, 50));
		title.setForeground(new Color(28, 28, 28));
		mode.setBackground(new Color(0, 154, 205));
		
		mode.add(modeOne);
		modeOne.setBorder(new EmptyBorder(15,25,15,25));
		modeOne.setForeground(new Color(185, 211, 238));
		modeOne.setBackground(new Color(16, 78, 139));
		modeOne.setFont(new Font("Consolas", Font.BOLD, 18));
		
		mode.add(modeTwo);
		modeTwo.setBorder(new EmptyBorder(15,25,15,25));
		modeTwo.setForeground(new Color(185, 211, 238));
		modeTwo.setBackground(new Color(16, 78, 139));
		modeTwo.setFont(new Font("Consolas", Font.BOLD, 18));
		
		modeOne.addActionListener(new ModeListener());
		modeTwo.addActionListener(new ModeListener());
		
		setLayout(new BorderLayout(0,0));
		add(mode, BorderLayout.CENTER);
		add(st, BorderLayout.NORTH);
		add(ifor, BorderLayout.SOUTH);
		
		st.setPreferredSize(new Dimension(0, 40));
		st.setBackground(new Color(16, 78, 139));
		ifor.setPreferredSize(new Dimension(0, 50));
		ifor.setBackground(new Color(16, 78, 139));
		
		
		setSize(600,600);
		setLocationRelativeTo(null);
		setTitle("TicTacToe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		st.add(status);
		ifor.add(ifo);
		st.add(TimePanel);
		setVisible(true);
	}
	
	
	
	//Thời gian
	
	public void timer() {
			timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				second++;
				wait--;
				time.setText("Time: "+minute+":"+ second + "      ");
				timeWait.setText(currentPlayer + ":" + wait);
				if(second==60){
					second=0;
					minute++;
					time.setText("Time: "+minute+":"+ second + "      ");
					timeWait.setText(currentPlayer + ":" + wait);
				}
				
				if(wait == 15) {
					JFrame frame = new JFrame();
					setLocationRelativeTo(null);
					JOptionPane.showMessageDialog(frame, "Vui lòng trở lại ván đấu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
				
				if(wait == 0) {
					timer.stop();		
					JFrame frame = new JFrame();
					setLocationRelativeTo(null);
					JOptionPane.showMessageDialog(frame, "Hết giờ! Player "+currentPlayer+" đã thua!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					if(currentPlayer == "X") {
						Ogoal++;
						if(onePlayer==true) {
							status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
						}else {
							status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
						}
						
					}else {
						Xgoal++;
						if(onePlayer==true) {
							status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
						}else {
							status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
						}
					}
				}
					
			}
		});
	}
	
	
	//Khởi tạo ván chơi
	
	private void khoitao() {
		currentPlayer = "X";	
		winPlayer = "";
		movesLeft = 9;
		gameNotOver = true;
		time.setText("Time: "+minute+":"+second+ "      ");
		timeWait.setText(currentPlayer + ":0");
		time.setFont(new Font("Segoe Script", Font.BOLD, 18));
		timeWait.setFont(new Font("Segoe Script", Font.BOLD, 16));
		for(int x = 0; x < 3; ++x){
			for(int y = 0; y < 3; ++y){
				buttons[y][x].setText("");
				buttons[0][0].setBackground(new Color(164, 211, 238));
				buttons[2][0].setBackground(new Color(164, 211, 238));
				buttons[1][1].setBackground(new Color(164, 211, 238));
				buttons[0][2].setBackground(new Color(164, 211, 238));
				buttons[2][2].setBackground(new Color(164, 211, 238));
			}
		}
	}
	
	//Kiểm tra xem còn ô trống để di chuyển hay không
	//đúng nếu có thể di chuyển, sai nếu không
	
	private boolean isMoveAllowed(int x, int y) {
		if(buttons[y][x].getText() == ""){
			return true;
		} else {
			return false;
		}
	}
	

	// Đánh dấu ô là người chơi di chuyển hiện tại 

	private void Move(int x, int y) {
		Font f = new Font("Gadugi", Font.BOLD, 90);
		buttons[y][x].setText(currentPlayer);
		buttons[y][x].setFont(f);
		if(currentPlayer == "X") {
			buttons[y][x].setForeground(new Color(165, 42, 42));
		}else {
			buttons[y][x].setForeground(new Color(65, 105, 225));
		}
		movesLeft--; // giảm số ô trống di chuyển
	}
	
	//Thay đổi lượt chơi
	 
	private void changePlayer() {
		if(currentPlayer == "X"){
			currentPlayer = "O";
			if(currentPlayer == "O") { //bắt đầu thời gian chờ của O
			wait=30;
			wait--;
			time.setText("Time: "+minute+":"+ second + "      ");
			timeWait.setText(currentPlayer + ":" + wait);
			}
		} else {
			currentPlayer = "X";		
			if(currentPlayer == "X") { //bắt đầu thời gian chờ của X
			wait=30;
			wait--;
			time.setText("Time: "+minute+":"+ second + "      ");
			timeWait.setText(currentPlayer + ":" + wait);
		}
		}
	}
	

	// Kiểm tra xem trò chơi đã kết thúc chưa
	// true nếu trò chơi kết thúc, ngược lại false

	private boolean isGameOver() {
		if(playerWon() || noMovesLeft()){
			gameNotOver = false;
			return true;
		} else {
			gameNotOver = true;
			return false;
		}
	}
	
	// kiểm tra chiến thắng
	
	private boolean playerWon() {
		// hàng ngang
		if(buttons[0][0].getText() == currentPlayer &&
				buttons[1][0].getText() == currentPlayer &&
				buttons[2][0].getText() == currentPlayer) {
			buttons[0][0].setBackground(new Color(0, 205, 0));
			buttons[1][0].setBackground(new Color(0, 205, 0));
			buttons[2][0].setBackground(new Color(0, 205, 0));
			buttons[1][1].setBackground(new Color(164, 211, 238));
			buttons[0][2].setBackground(new Color(164, 211, 238));
			buttons[2][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		else if(buttons[0][1].getText() == currentPlayer &&
				buttons[1][1].getText() == currentPlayer &&
				buttons[2][1].getText() == currentPlayer) {
			buttons[0][1].setBackground(new Color(0, 205, 0));
			buttons[1][1].setBackground(new Color(0, 205, 0));
			buttons[2][1].setBackground(new Color(0, 205, 0));
			buttons[0][0].setBackground(new Color(164, 211, 238));
			buttons[2][0].setBackground(new Color(164, 211, 238));
			buttons[0][2].setBackground(new Color(164, 211, 238));
			buttons[2][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		else if(buttons[0][2].getText() == currentPlayer &&
				buttons[1][2].getText() == currentPlayer &&
				buttons[2][2].getText() == currentPlayer) {
			buttons[0][2].setBackground(new Color(0, 205, 0));
			buttons[1][2].setBackground(new Color(0, 205, 0));
			buttons[2][2].setBackground(new Color(0, 205, 0));		
			buttons[0][0].setBackground(new Color(164, 211, 238));
			buttons[2][0].setBackground(new Color(164, 211, 238));
			buttons[1][1].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		// hàng dọc

		else if(buttons[0][0].getText() == currentPlayer &&
				buttons[0][1].getText() == currentPlayer &&
				buttons[0][2].getText() == currentPlayer) {
			buttons[0][0].setBackground(new Color(0, 205, 0));
			buttons[0][1].setBackground(new Color(0, 205, 0));
			buttons[0][2].setBackground(new Color(0, 205, 0));
			buttons[1][0].setBackground(null);
			buttons[1][2].setBackground(null);
			buttons[1][1].setBackground(new Color(164, 211, 238));
			buttons[2][0].setBackground(new Color(164, 211, 238));
			buttons[2][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		else if(buttons[1][0].getText() == currentPlayer &&
				buttons[1][1].getText() == currentPlayer &&
				buttons[1][2].getText() == currentPlayer) {
			buttons[1][0].setBackground(new Color(0, 205, 0));
			buttons[1][1].setBackground(new Color(0, 205, 0));
			buttons[1][2].setBackground(new Color(0, 205, 0));
			buttons[0][0].setBackground(new Color(164, 211, 238));
			buttons[2][0].setBackground(new Color(164, 211, 238));
			buttons[0][2].setBackground(new Color(164, 211, 238));
			buttons[2][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		else if(buttons[2][0].getText() == currentPlayer &&
				buttons[2][1].getText() == currentPlayer &&
				buttons[2][2].getText() == currentPlayer) {
			buttons[2][0].setBackground(new Color(0, 205, 0));
			buttons[2][1].setBackground(new Color(0, 205, 0));
			buttons[2][2].setBackground(new Color(0, 205, 0));
			buttons[1][0].setBackground(null);
			buttons[1][2].setBackground(null);
			buttons[0][0].setBackground(new Color(164, 211, 238));
			buttons[1][1].setBackground(new Color(164, 211, 238));
			buttons[0][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		}
		
		// chéo phải
		
		else if(buttons[0][0].getText() == currentPlayer &&
				buttons[1][1].getText() == currentPlayer &&
				buttons[2][2].getText() == currentPlayer) {
			buttons[0][0].setBackground(new Color(0, 205, 0));
			buttons[1][1].setBackground(new Color(0, 205, 0));
			buttons[2][2].setBackground(new Color(0, 205, 0));
			buttons[1][0].setBackground(null);
			buttons[2][0].setBackground(new Color(164, 211, 238));
			buttons[0][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		} 

		// chéo trái

		else if(buttons[2][0].getText() == currentPlayer &&
				buttons[1][1].getText() == currentPlayer &&
				buttons[0][2].getText() == currentPlayer) {
			buttons[2][0].setBackground(new Color(0, 205, 0));
			buttons[1][1].setBackground(new Color(0, 205, 0));
			buttons[0][2].setBackground(new Color(0, 205, 0));
			buttons[0][0].setBackground(new Color(164, 211, 238));
			buttons[2][2].setBackground(new Color(164, 211, 238));
			winPlayer = currentPlayer;
			return true;
		} else {
			return false;
			
		}
		
		
	}
	
	//Kiểm tra nếu còn ô trống thì trò chơi chưa kết thúc
	
	private boolean noMovesLeft() {
		for(int x = 0; x < 3; ++x){
			for(int y = 0; y < 3; ++y){
				if(buttons[y][x].getText() == ""){
					return false;
				}
			}
		}
		return true;
	}
	
	//Nước di chuyển của máy
	private int[] getMove() {
		int[] result = minimax(2, computerPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] {result[1], result[2]};
	}
	
	//Giải thuật minimax
	private int[] minimax(int depth, String player, int alpha, int beta) {
		ArrayList<int[]> moves = generateMoves(); 
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;
		String opponent = (player == computerPlayer) ? "X" : computerPlayer; //gán đối thủ
		
		if (moves.isEmpty() || depth == 0) {
			currentScore = scoreBoard();
			return new int[] {currentScore, bestRow, bestCol};
		} else {
			for(int[] move : moves) {
				buttons[move[0]][move[1]].setText(player);
	            if (player == computerPlayer) {  
	               currentScore = minimax(depth - 1, opponent, alpha, beta)[0];
	               if (currentScore > alpha) {
	                  alpha = currentScore;
	                  bestRow = move[0];
	                  bestCol = move[1];
	               }
	            } else {  
	               currentScore = minimax(depth - 1, opponent, alpha, beta)[0];
	               if (currentScore < beta) {
	                  beta = currentScore;
	                  bestRow = move[0];
	                  bestCol = move[1];
	               }
	            }//Hoàn tác di chuyển
	            buttons[move[0]][move[1]].setText("");
	            if(alpha >= beta) break;
			}
		}
		System.out.println("Ket thuc di chuyen cua may");
		return new int[] {(player == computerPlayer) ? alpha : beta, bestRow, bestCol};
	}
	
	// Mảng các nước đi
	private ArrayList<int[]> generateMoves() {
		ArrayList<int[]> moveList = new ArrayList<int[]>();
		if (isGameOver()){
			System.out.println("Tro choi ket thuc ");
			return moveList;
		}
		for(int x = 0; x < 3; ++x){
			for(int y = 0; y < 3; ++y){
				if(buttons[y][x].getText() == "") {
					moveList.add(new int[] {y, x});
					System.out.println("moves " + x + y);
				}
			}
		}
		return moveList;
	}
	
	//Bảng điểm
	
	private int scoreBoard() {
		int score = 0;

        //Kiểm tra tất cả các hàng
        for (int y = 0; y < 3; ++y) {
            int X = 0;
            int O = 0;
            for (int x = 0; x < 3; ++x) {
                if (buttons[y][x].getText() == "O") {
                	O++;
                } else if (buttons[y][x].getText() == "X") {
                    X++;
                }
            } 
            score+=changeInScore(X, O); 
        }

        //Kiểm tra tất cả các cột
        for (int x = 0; x < 3; ++x) {
            int X = 0;
            int O = 0;
            for (int y = 0; y < 3; ++y) {
                if (buttons[y][x].getText() == "") {
                	O++;
                } else if (buttons[y][x].getText() == "X") {
                    X++;
                }
            }
            score+=changeInScore(X, O);
        }

        int X = 0;
        int O = 0;

        //Kiểm tra đường chéo đầu tiên
        for (int y = 0, x = 0; y < 3; ++y, ++x) {
            if (buttons[y][x].getText() == "X") {
                X++;
            } else if (buttons[y][x].getText() == "O") {
                O++;
            }
        }

        score+=changeInScore(X, O);

        X = 0;
        O = 0;

        //Kiểm tra đường chéo thứ hai
        for (int y = 2, x = 0; y > -1; --y, ++x) {
            if (buttons[y][x].getText() == "X") {
                X++;
            } else if (buttons[y][x].getText() == "O") {
                O++;
            }
        }

        score+=changeInScore(X, O);
        return score;
	}
	
	//Thay đổi điểm số
	private int changeInScore(int X, int O){
        int change;
        if (O == 3) {
            change = 100;
        } else if (O == 2 && X == 0) {
            change = 10;
        } else if (O == 1 && X == 0) {
            change = 1;
        } else if (X == 3) {
            change = -100;
        } else if (X == 2 && O == 0) {
            change = -10;
        } else if (X == 1 && O == 0) {
            change = -1;
        } else {
            change = 0;
        } 
        return change;
    }
	
	//Kiểm tra chiến thắng và di chuyển
	
	private void checkAndMove(int currentX, int currentY) {
		if(!isMoveAllowed(currentX, currentY) && gameNotOver) {
			status.setText("Invalid move");
		} else if(gameNotOver){
			Move(currentX, currentY);
			if(isGameOver()){
				if(winPlayer != ""){
					timer.stop();
					JFrame frame = new JFrame();
					frame.setLocationRelativeTo(null);
					JOptionPane.showMessageDialog(frame,"Player " + winPlayer+" chiến thắng!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					if(winPlayer == "X") {
						Xgoal++;
						status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
					}else{
						Ogoal++;
						status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
					}
					
				} else {
					buttons[0][0].setBackground(new Color(164, 211, 238));
					buttons[2][0].setBackground(new Color(164, 211, 238));
					buttons[1][1].setBackground(new Color(164, 211, 238));
					buttons[0][2].setBackground(new Color(164, 211, 238));
					buttons[2][2].setBackground(new Color(164, 211, 238));	
					buttons[1][0].setBackground(new Color(255, 185, 15));
					buttons[0][1].setBackground(new Color(255, 185, 15));
					buttons[2][1].setBackground(new Color(255, 185, 15));
					buttons[1][2].setBackground(new Color(255, 185, 15));
					timer.stop();
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Hòa nhau!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				changePlayer();
			}
		}
	}
	
	
	class TicTacToeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int currentX = 0;
			int currentY = 0;
			for(int x = 0; x < 3; ++x){
				for(int y = 0; y < 3; ++y){
					if(e.getSource() == buttons[y][x]){
						currentX = x;
						currentY = y;
					}
				}
			}
			
			checkAndMove(currentX, currentY);
			revalidate();
			if(onePlayer == true && gameNotOver) {
				int[] computerMove = getMove();
				currentX = computerMove[1];
				currentY = computerMove[0];
				System.out.println("Di chuyen: "+currentY+currentX);
				gameNotOver = true;
				checkAndMove(currentX, currentY);
				
				
			}
			
			if(onePlayer==true) {
				status.setText("Player(X): "+Xgoal+"      "+"Computer(O): "+Ogoal+"      ");
				if(winPlayer == "X") {
					status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
				}else{
					status.setText("Player(X): "+Xgoal+"     "+"Computer(O): "+Ogoal+"      ");
				}
			}else {
				status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
				if(winPlayer == "X") {
					status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
				}else{
					status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
				}
			}
			
			
		}

	}
	
	class ModeListener implements ActionListener {
		@Override
		
		public void actionPerformed(ActionEvent e) {
			board = new JPanel(new GridLayout(3,3));
			buttons = new JButton[3][3];
			for(int x = 0; x < 3; ++x){
				for(int y = 0; y < 3; ++y){
					buttons[y][x] = new JButton();
					buttons[y][x].addActionListener(new TicTacToeListener());
					board.add(buttons[y][x]);
				}
			}
			remove(mode);
			add(board, BorderLayout.CENTER);
			
			//Tạo bảng điều khiển cho các nút trong game
			Pane = new JPanel();
			Pane.setLayout(new GridLayout(1, 4));
			Pane.setPreferredSize(new Dimension(50, 40));
			add(Pane, BorderLayout.SOUTH);
			
			//Nút reset
			reset = new JButton(new AbstractAction("Reset") {
			    public void actionPerformed(ActionEvent e) {
			    	khoitao();
			    	for(int x = 0; x < 3; ++x){
						for(int y = 0; y < 3; ++y){
							buttons[0][0].setBackground(new Color(164, 211, 238));
							buttons[2][0].setBackground(new Color(164, 211, 238));
							buttons[1][1].setBackground(new Color(164, 211, 238));
							buttons[0][2].setBackground(new Color(164, 211, 238));
							buttons[2][2].setBackground(new Color(164, 211, 238));	
							buttons[1][0].setBackground(null);
							buttons[0][1].setBackground(null);
							buttons[2][1].setBackground(null);
							buttons[1][2].setBackground(null);
							timer.stop();
							minute=0;
							second=0;
							wait=0;
							time.setText("Time: "+minute+":"+ second + "      ");
							timeWait.setText(currentPlayer + ":" + wait);
							wait=30;
						}
					}
			    	
			    }
			});
			reset.setBackground(new Color(16, 78, 139));
			reset.setForeground(new Color(185, 211, 238));
			reset.setFont(new Font("Consolas", Font.BOLD, 15));
			board.add(reset, BorderLayout.SOUTH );
			Pane.add(reset);
			
			//Nút hướng dẫn
			help = new JButton(new AbstractAction("Help") {
			    public void actionPerformed(ActionEvent e) {
			    	JFrame frame = new JFrame();
					setLocationRelativeTo(null);
					JOptionPane.showMessageDialog(frame, "Hai người chơi sẽ lần lượt đi các nước cờ X hoặc O xen kẽ nhau.\nNgười chơi nào có quân cờ liên tiếp lấp đầy"
   			             								+ "  cột dọc, hàng ngang,\nđường chéo trái hoặc đường chéo phải trước tiên sẽ là người\nchiến thắng!", "Luật chơi", JOptionPane.INFORMATION_MESSAGE);
			    }
			});
			help.setBackground(new Color(16, 78, 139));
			help.setForeground(new Color(185, 211, 238));
			help.setFont(new Font("Consolas", Font.BOLD, 15));
			board.add(help, BorderLayout.SOUTH);
			Pane.add(help);
			
			//Nút bắt đầu tính điểm
			start = new JButton(new AbstractAction("Start") {
			    public void actionPerformed(ActionEvent e) {
					timer.start();
			    }
			});
			start.setBackground(new Color(16, 78, 139));
			start.setForeground(new Color(185, 211, 238));
			start.setFont(new Font("Consolas", Font.BOLD, 15));
			board.add(start, BorderLayout.SOUTH );
			Pane.add(start);
			
			//Nút thoát trò chơi
			exit = new JButton(new AbstractAction("Exit") {
			    public void actionPerformed(ActionEvent e) {
			    	System.exit(0);		
			    }    
			});
			exit.setBackground(new Color(16, 78, 139));
			exit.setForeground(new Color(185, 211, 238));
			exit.setFont(new Font("Consolas", Font.BOLD, 15));
			board.add(exit, BorderLayout.SOUTH);
			Pane.add(exit);
				
			khoitao();
			timer();
			if(e.getSource() == modeOne) {
				onePlayer = true;
				computerPlayer = "O";
				status.setText("Player(X): "+Xgoal+"      "+"Computer(O): "+Ogoal+"      ");
			} else if(e.getSource() == modeTwo) {
				onePlayer = false;
				status.setText("Player 1(X): "+Xgoal+"      "+"Player 2(O): "+Ogoal+"      ");
			}
		}

	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		TicTacToe tttBoard = new TicTacToe();
	}
}