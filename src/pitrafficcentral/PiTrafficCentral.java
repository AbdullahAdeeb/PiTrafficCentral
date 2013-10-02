package pitrafficcentral;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * This is the main class for the traffic central This will start the gui and
 * handles the timers for the traffic
 *
 * @author lenovo212
 */
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

    /**
     * Init. the timers and the gui
     *
     * @param args
     */
    public static void main(String[] args) {
        udpSender = new TransmissionControl();
        PiTrafficCentral.greenTransmission[0] = 49;
        PiTrafficCentral.yellowTransmission[0] = 50;
        PiTrafficCentral.redTransmission[0] = 51;

        pedsTimer = new Timer(PEDS_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (yellowTimer.isRunning() || graceTimer.isRunning() || !isOn) {
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
                }else{
                    System.out.println("Ignoring pedestrain input");
                }

                pedsTimer.stop();

            }
        });

        yellowTimer = new Timer(YELLOW_DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!isOn) {
                    return;
                }
                if (isVerticleGreen) {
                    setVRed();
                } else {
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
                if (!isOn) {
                    return;
                }
                if (isVerticleGreen) {
                    setVYellow();
                } else {
                    setHYellow();
                }
                yellowTimer.start();
                greenTimer.stop();
                pedsTimer.stop();
            }
        });

        startGUI();

    }

        /**
     * starts the traffic central gui
     */
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

    /**
     * called when data is relieved through UDP
     * @param str
     */
    protected static void rcvData(String str) {
        // str = 1 >> vertical
        pedsCode = Integer.parseInt(str);
        pedsTimer.start();
    }

       /**
     * Called to change the south-north light to green
     * 
     */
    private static void setVGreen() {
        System.out.println("red >> green");

        trafficControlGUI.setGreen(trafficControlGUI.getTrafficTop());
        trafficControlGUI.setGreen(trafficControlGUI.getTrafficBottom());
        isVerticleGreen = true;
        udpSender.sendV(greenTransmission);
    }

           /**
     * Called to change the south-north light to yellow
     * 
     */
    private static void setVYellow() {
        System.out.println("green >> yellow");
        trafficControlGUI.setYellow(trafficControlGUI.getTrafficTop());
        trafficControlGUI.setYellow(trafficControlGUI.getTrafficBottom());
        udpSender.sendV(yellowTransmission);
    }

           /**
     * Called to change the south-north light to red
     * 
     */
    private static void setVRed() {
        System.out.println("yellow >> red");
        trafficControlGUI.setRed(trafficControlGUI.getTrafficTop());
        trafficControlGUI.setRed(trafficControlGUI.getTrafficBottom());
        udpSender.sendV(redTransmission);
    }

           /**
     * Called to change the east-west light to green
     * 
     */
    private static void setHGreen() {
        System.out.println("red >> green");

        trafficControlGUI.setGreen(trafficControlGUI.getTrafficRight());
        trafficControlGUI.setGreen(trafficControlGUI.getTrafficLeft());
        udpSender.sendH(greenTransmission);
    }

               /**
     * Called to change the east-west light to yellow
     * 
     */
    private static void setHYellow() {
        System.out.println("green >> yellow");
        trafficControlGUI.setYellow(trafficControlGUI.getTrafficRight());
        trafficControlGUI.setYellow(trafficControlGUI.getTrafficLeft());
        udpSender.sendH(yellowTransmission);
    }

               /**
     * Called to change the east-west light to red
     * 
     */
    private static void setHRed() {
        System.out.println("yellow >> red");
        trafficControlGUI.setRed(trafficControlGUI.getTrafficRight());
        trafficControlGUI.setRed(trafficControlGUI.getTrafficLeft());
        udpSender.sendH(redTransmission);
    }

    /**
     * return the running GUI
     * @return
     */
    public static TrafficCentralGUI getTrafficControlGUI() {
        return trafficControlGUI;
    }

    /**
     * return the running UDP controller 
     * @return
     */
    public static UdpControl getUdpC() {
        return udpC;
    }

    /**
     * stops the simulation
     */
    public static void stopSim() {
        greenTimer.stop();
        yellowTimer.stop();
        
        isOn = false;

    }

    /**
     * starts the simulation
     */
    public static void startSim() {
        if (!isOn) {
            isOn = true;
            greenTimer.start();
        }
    }
}