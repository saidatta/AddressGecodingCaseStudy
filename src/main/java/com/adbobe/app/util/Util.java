package com.adbobe.app.util;


import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

public class Util {

    private static String hostName = null;

    // https://github.com/cowtowncoder/java-uuid-generator
    private static final TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    /**
     * Look-up the hostname of the server using one of several methods; the using <code>getLocalHost()</code> followed
     * by <code>getHostName()</code> doesn't work if <i>Reverse DNS</i> is set up improperly.
     *
     * @return String hostname
     */
    public static String getHostname() {

        // First try property
        if (hostName == null) {

            hostName = System.getProperty("HOSTNAME");
        }

        // Next try environment
        if (hostName == null) {

            hostName = System.getenv("HOSTNAME");
        }

        // Assume we're a standard POSIX system and run uname -n
        if (hostName == null) {

            try {

                Process uname = Runtime.getRuntime().exec("uname -n");

                BufferedReader input = new BufferedReader(new InputStreamReader(uname.getInputStream()));

                hostName = input.readLine();
                int ret = uname.waitFor();

                if (ret != 0)
                    hostName = null;
            } catch (Exception e) {

            }
        }

        // As a last resort, try a reverse DNS look-up which will often fail
        if (hostName == null) {

            try {
                hostName = java.net.InetAddress.getLocalHost().getHostName();
            } catch (java.net.UnknownHostException e) {

            }
        }

        return hostName;
    }

    public static String getMacAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface inetAddress = NetworkInterface.getByInetAddress(localHost);

            byte[] hardwareAddress = inetAddress.getHardwareAddress();

            if (hardwareAddress == null) {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress != null) {
                        break;
                    }
                }
            }

            return hardwareAddress != null ? getHex(hardwareAddress) : "NO_MAC_ADDRESS";

        } catch (Exception e) {
        }

        return "NO_MAC_ADDRESS";
    }

    private static String getHex(byte[] bytes) {
        StringBuilder result = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // Upcast to make unsigned
            short s = (short)(b & 0xff);
            result.append(Integer.toHexString(s));
        }
        return result.toString();
    }

    public static UUID getUuid() {
        return uuidGenerator.generate();
    }
}
