package pitrafficcentral;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class TrafficCentralGUI extends JFrame {
	static final String Intersection_Image_Path = "res/inter.jpg";
	static final String Red_Light_Image_Path = "res/redLight.png";
	static final String Yellow_Light_Image_Path = "res/yellowLight.png";
	static final String Green_Light_Image_Path = "res/greenLight.png";

	private BufferedImage redLightImg;
	private BufferedImage yellowLightImg;
	private BufferedImage greenLightImg;
	
	

//	private ArrayList<JPanel> trafficList;


	private JButton changeTrafficFlowButton;
	private JPanel displayPanel;
	private JButton jButton1;
	private JButton startSimulationButton;

	


	private JPanel trafficBottom;
	private JPanel trafficLeft;
	private JPanel trafficRight;
	private JPanel trafficTop;
	
	public TrafficCentralGUI() {


		initMyCompnents();
		initComponents();
		changeTrafficFlowButton.setVisible(false);

		}

	private void initMyCompnents() {
		try {
			this.redLightImg = ImageIO.read(new File("res/redLight.png"));
			this.yellowLightImg = ImageIO.read(new File("res/yellowLight.png"));
			this.greenLightImg = ImageIO.read(new File("res/greenLight.png"));
		} catch (IOException ex) {
			Logger.getLogger(TrafficCentralGUI.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	private void initComponents() {
		this.changeTrafficFlowButton = new JButton();
		this.displayPanel = new ImagePanel("res/inter.jpg");
		this.setTrafficTop(new TrafficLight(this.greenLightImg, 0));
		this.setTrafficLeft(new TrafficLight(this.redLightImg, 2));
		this.setTrafficBottom(new TrafficLight(this.greenLightImg, 0));
		this.setTrafficRight(new TrafficLight(this.redLightImg, 1));
		this.startSimulationButton = new JButton();
		this.jButton1 = new JButton();

		setDefaultCloseOperation(3);
		setTitle("RPi Traffic Control Central");
		setBackground(new Color(0, 0, 0));

		this.changeTrafficFlowButton.setText("Change Traffic Flow");
		this.changeTrafficFlowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TrafficCentralGUI.this
						.changeTrafficFlowButtonActionPerformed(evt);
			}
		});
		this.displayPanel.setBorder(BorderFactory.createLineBorder(new Color(0,
				0, 0)));
		this.displayPanel.setPreferredSize(new Dimension(600, 600));

		this.getTrafficTop().setBackground(new Color(0, 0, 0));
		this.getTrafficTop().setPreferredSize(new Dimension(53, 100));

		GroupLayout trafficTopLayout = new GroupLayout(this.getTrafficTop());
		this.getTrafficTop().setLayout(trafficTopLayout);
		trafficTopLayout.setHorizontalGroup(trafficTopLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						53, 32767));

		trafficTopLayout.setVerticalGroup(trafficTopLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 100, 32767));

		this.getTrafficLeft().setBackground(new Color(0, 0, 0));
		this.getTrafficLeft().setMaximumSize(new Dimension(100, 53));
		this.getTrafficLeft().setMinimumSize(new Dimension(100, 53));
		this.getTrafficLeft().setPreferredSize(new Dimension(100, 53));

		GroupLayout trafficLeftLayout = new GroupLayout(this.getTrafficLeft());
		this.getTrafficLeft().setLayout(trafficLeftLayout);
		trafficLeftLayout.setHorizontalGroup(trafficLeftLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						100, 32767));

		trafficLeftLayout.setVerticalGroup(trafficLeftLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						53, 32767));

		this.getTrafficBottom().setBackground(new Color(0, 0, 0));
		this.getTrafficBottom().setPreferredSize(new Dimension(53, 100));

		GroupLayout trafficBottomLayout = new GroupLayout(this.getTrafficBottom());
		this.getTrafficBottom().setLayout(trafficBottomLayout);
		trafficBottomLayout.setHorizontalGroup(trafficBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						53, 32767));

		trafficBottomLayout.setVerticalGroup(trafficBottomLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						100, 32767));

		this.getTrafficRight().setBackground(new Color(0, 0, 0));
		this.getTrafficRight().setPreferredSize(new Dimension(100, 53));

		GroupLayout trafficRightLayout = new GroupLayout(this.getTrafficRight());
		this.getTrafficRight().setLayout(trafficRightLayout);
		trafficRightLayout.setHorizontalGroup(trafficRightLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						100, 32767));

		trafficRightLayout.setVerticalGroup(trafficRightLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						53, 32767));

		GroupLayout displayPanelLayout = new GroupLayout(this.displayPanel);
		this.displayPanel.setLayout(displayPanelLayout);
		displayPanelLayout
				.setHorizontalGroup(displayPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								GroupLayout.Alignment.TRAILING,
								displayPanelLayout
										.createSequentialGroup()
										.addContainerGap(149, 32767)
										.addGroup(
												displayPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																displayPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.getTrafficLeft(),
																				-2,
																				-1,
																				-2)
																		.addGap(74,
																				74,
																				74)
																		.addComponent(
																				this.getTrafficBottom(),
																				-2,
																				-1,
																				-2)
																		.addGap(222,
																				222,
																				222))
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																displayPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.getTrafficTop(),
																				-2,
																				-1,
																				-2)
																		.addGap(94,
																				94,
																				94)
																		.addComponent(
																				this.getTrafficRight(),
																				-2,
																				-1,
																				-2)
																		.addGap(139,
																				139,
																				139)))));

		displayPanelLayout
				.setVerticalGroup(displayPanelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								displayPanelLayout
										.createSequentialGroup()
										.addGroup(
												displayPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																displayPanelLayout
																		.createSequentialGroup()
																		.addGap(217,
																				217,
																				217)
																		.addComponent(
																				this.getTrafficRight(),
																				-2,
																				-1,
																				-2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				62,
																				32767))
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																displayPanelLayout
																		.createSequentialGroup()
																		.addContainerGap(
																				-1,
																				32767)
																		.addComponent(
																				this.getTrafficTop(),
																				-2,
																				-1,
																				-2)
																		.addGap(84,
																				84,
																				84)))
										.addGroup(
												displayPanelLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																displayPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.getTrafficLeft(),
																				-2,
																				-1,
																				-2)
																		.addGap(213,
																				213,
																				213))
														.addGroup(
																GroupLayout.Alignment.TRAILING,
																displayPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.getTrafficBottom(),
																				-2,
																				-1,
																				-2)
																		.addGap(152,
																				152,
																				152)))));

		this.startSimulationButton.setText("Start Simulation");
		this.startSimulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TrafficCentralGUI.this
						.startSimulationButtonActionPerformed(evt);
			}
		});
		this.jButton1.setText("Stop Simulation");
		this.jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				TrafficCentralGUI.this.jButton1ActionPerformed(evt);
			}
		});
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														this.displayPanel, -2,
														-1, -2)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		this.changeTrafficFlowButton,
																		-2,
																		201, -2)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.jButton1,
																		-1, -1,
																		32767)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		this.startSimulationButton,
																		-2,
																		184, -2)))
								.addContainerGap(-1, 32767)));

		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(this.displayPanel, -2, -1, -2)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														this.changeTrafficFlowButton,
														-1, 52, 32767)
												.addComponent(
														this.startSimulationButton,
														-1, -1, 32767)
												.addComponent(this.jButton1,
														-1, -1, 32767))
								.addContainerGap(-1, 32767)));

		
		pack();
	}

	private void changeTrafficFlowButtonActionPerformed(ActionEvent evt) {
		switchTrafficFlow();

	}

	private void startSimulationButtonActionPerformed(ActionEvent evt) {
		PiTrafficCentral.startSim();
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		PiTrafficCentral.stopSim();
		
	}



	private void switchTrafficFlow() {
//		// for (int i = 0; i < this.trafficList.size(); i++) {
//		// JPanel traf = (JPanel)this.trafficList.get(i);
//		// int status = getTrafficLightStatus(traf);
//		// if ((status == 658) || (status == 212))
//		// setGreen(traf);
//		// else if (status == 113)
//		// setRed(traf);
//		// }
//		setHGreen();
//		setVRed();
	}



	protected void setRed(JPanel traffic) {
		((TrafficLight) traffic).setLight(this.redLightImg);

	}

	protected void setYellow(JPanel traffic) {
		((TrafficLight) traffic).setLight(this.yellowLightImg);
		// send udp message '2'
	}

	protected void setGreen(JPanel traffic) {
		((TrafficLight) traffic).setLight(this.greenLightImg);
		// send udp message '1'
	}

	private int getTrafficLightStatus(JPanel trafficLight) {
		TrafficLight traffic = (TrafficLight) trafficLight;
		if (traffic.getImg() == this.redLightImg)
			return 658;
		if (traffic.getImg() == this.yellowLightImg)
			return 212;
		if (traffic.getImg() == this.greenLightImg) {
			return 113;
		}
		return 666;
	}

	public JPanel getTrafficRight() {
		return trafficRight;
	}

	public void setTrafficRight(JPanel trafficRight) {
		this.trafficRight = trafficRight;
	}

	public JPanel getTrafficLeft() {
		return trafficLeft;
	}

	public void setTrafficLeft(JPanel trafficLeft) {
		this.trafficLeft = trafficLeft;
	}

	public JPanel getTrafficTop() {
		return trafficTop;
	}

	public void setTrafficTop(JPanel trafficTop) {
		this.trafficTop = trafficTop;
	}

	public JPanel getTrafficBottom() {
		return trafficBottom;
	}

	public void setTrafficBottom(JPanel trafficBottom) {
		this.trafficBottom = trafficBottom;
	}

	class ImagePanel extends JPanel {
		private Image img;

		public ImagePanel(String img) {
			this(new ImageIcon(img).getImage());
		}

		public ImagePanel(Image img) {
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null),
					img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(this.img, 0, 0, null);
		}
	}

	class TrafficLight extends JPanel {
		private BufferedImage img;
		private int rotate = 0;
		public static final int RED = 658;
		public static final int YELLOW = 212;
		public static final int GREEN = 113;
		public static final int ERROR = 666;

		public TrafficLight(BufferedImage image, int rotate) {
			setLight(image);
			this.rotate = rotate;
		}

		public Dimension getPreferredSize() {
			if ((this.rotate == 1) || (this.rotate == 2)) {
				Dimension dimension = new Dimension(this.img.getHeight(),
						this.img.getWidth());
				return dimension;
			}

			return new Dimension(this.img.getWidth(), this.img.getHeight());
		}

		public void paintComponent(Graphics g) {
			if (this.rotate == 1) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(-1.570796326794897D, this.img.getWidth() / 2,
						this.img.getHeight() / 2);
				g2.drawImage(this.img, 25, 25, null);
			} else if (this.rotate == 2) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.rotate(1.570796326794897D, this.img.getWidth() / 2,
						this.img.getHeight() / 2);
				g2.drawImage(this.img, -25, -25, null);
			} else {
				g.drawImage(this.img, 0, 0, null);
			}
		}

		public void setLight(BufferedImage light) {
			this.img = light;
			repaint();
		}

		public BufferedImage getImg() {
			return this.img;
		}
	}

}