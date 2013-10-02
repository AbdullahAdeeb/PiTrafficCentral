package pitrafficcentral;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PiTrafficCentral {
	static TrafficCentralGUI trafficControlGUI;
	static UdpControl udpC;
	static TransmissionControl udpSender;

	private static final int GREEN_DELAY = 7000;
	private static final int YELLOW_DELAY = 3000;
	private static final int GRACE_DELAY = 1000;
	private static final int PEDS_DELAY = 1500;

	private static boolean isOn = false;

	private static Timer pedsTimer;
	private static Timer yellowTimer;
	private static Timer greenTimer;
	private static Timer graceTimer;

	private static int pedsCode;
	private static boolean isVerticleGreen;

	static byte[] greenTransmission = new byte[1];
	static byte[] yellowTransmission = new byte[1];
	static byte[] redTransmission = new byte[1];

	public static void main(String[] args) {
		udpSender = new TransmissionControl();
		PiTrafficCentral.greenTransmission[0] = 49;
		PiTrafficCentral.yellowTransmission[0] = 50;
		PiTrafficCentral.redTransmission[0] = 51;

		pedsTimer = new Timer(PEDS_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (yellowTimer.isRunning() || graceTimer.isRunning()) {
					pedsTimer.stop();
					return;
				}
				if (isVerticleGreen && pedsCode == 2) {
					greenTimer.stop();
					setVYellow();
					yellowTimer.start();
				} else if (!isVerticleGreen && pedsCode == 1) {
					greenTimer.stop();
					setHYellow();
					yellowTimer.start();
				}

				pedsTimer.stop();

			}
		});

		yellowTimer = new Timer(YELLOW_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isVerticleGreen)
					setVRed();

				else {
					setHRed();
				}
				yellowTimer.stop();
				graceTimer.start();
			}
		});
		graceTimer = new Timer(GRACE_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isVerticleGreen) {
					setHGreen();
					isVerticleGreen = false;
				} else {
					setVGreen();
					isVerticleGreen = true;
				}

				graceTimer.stop();
				greenTimer.start();
			}
		});

		greenTimer = new Timer(GREEN_DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isVerticleGreen)
					setVYellow();
				else {
					setHYellow();
				}
				yellowTimer.start();
				greenTimer.stop();
				pedsTimer.stop();
			}
		});

		startGUI();

	}

	private static void startGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				udpC = new UdpControl();

				trafficControlGUI = new TrafficCentralGUI();
				trafficControlGUI.setVisible(true);

				setVGreen();
				setHRed();
				isVerticleGreen = true;

			}
		});

	}

	public static void stopGreenTimer() {

	}

	protected static void rcvData(String str) {
		// str = 1 >> vertical
		pedsCode = Integer.parseInt(str);
		pedsTimer.start();
	}

	private static void setVGreen() {
		System.out.println("red >> green");

		trafficControlGUI.setGreen(trafficControlGUI.getTrafficTop());
		trafficControlGUI.setGreen(trafficControlGUI.getTrafficBottom());
		isVerticleGreen = true;
		udpSender.sendV(greenTransmission);
	}

	private static void setVYellow() {
		System.out.println("green >> yellow");
		trafficControlGUI.setYellow(trafficControlGUI.getTrafficTop());
		trafficControlGUI.setYellow(trafficControlGUI.getTrafficBottom());
		udpSender.sendV(yellowTransmission);
	}

	private static void setVRed() {
		System.out.println("yellow >> red");
		trafficControlGUI.setRed(trafficControlGUI.getTrafficTop());
		trafficControlGUI.setRed(trafficControlGUI.getTrafficBottom());
		udpSender.sendV(redTransmission);
	}

	private static void setHGreen() {
		System.out.println("red >> green");

		trafficControlGUI.setGreen(trafficControlGUI.getTrafficRight());
		trafficControlGUI.setGreen(trafficControlGUI.getTrafficLeft());
		udpSender.sendH(greenTransmission);
	}

	private static void setHYellow() {
		System.out.println("green >> yellow");
		trafficControlGUI.setYellow(trafficControlGUI.getTrafficRight());
		trafficControlGUI.setYellow(trafficControlGUI.getTrafficLeft());
		udpSender.sendH(yellowTransmission);
	}

	private static void setHRed() {
		System.out.println("yellow >> red");
		trafficControlGUI.setRed(trafficControlGUI.getTrafficRight());
		trafficControlGUI.setRed(trafficControlGUI.getTrafficLeft());
		udpSender.sendH(redTransmission);
	}

	public static TrafficCentralGUI getTrafficControlGUI() {
		return trafficControlGUI;
	}

	public static void setTrafficControlGUI(TrafficCentralGUI trafficControlGUI) {
		PiTrafficCentral.trafficControlGUI = trafficControlGUI;
	}

	public static UdpControl getUdpC() {
		return udpC;
	}

	public static void setUdpC(UdpControl udpC) {
		PiTrafficCentral.udpC = udpC;
	}

	public static void stopSim() {
		stopGreenTimer();
		yellowTimer.stop();
		isOn = false;

	}

	public static void startSim() {
		if (!isOn) {
			isOn = true;
			greenTimer.start();
		}
	}

}