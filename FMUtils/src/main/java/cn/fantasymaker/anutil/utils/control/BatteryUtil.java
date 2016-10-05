/*
 *     Copyright © 2016 Fantasymaker
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy
 *     of this software and associated documentation files (the "Software"), to deal
 *     in the Software without restriction, including without limitation the rights
 *     to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *     copies of the Software, and to permit persons to whom the Software is
 *     furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all
 *     copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *     SOFTWARE.
 */

package cn.fantasymaker.anutil.utils.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Get battery info<br/>
 * WARN: Must wait for a while after registering, then data can be got<br/>
 * <p>
 * Created :  2016-08-10
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class BatteryUtil {

    /*
    x获取电量
    x接收电量广播
    x获取电池温度
    x获取电池电压
    x获取电源类型
     */

    private static int sLevel = 0;
    private static int sScale = 100;
    private static int sHealth = BatteryManager.BATTERY_HEALTH_UNKNOWN;
    private static int sIconResId = 0;
    private static int sPlugType = 0;
    private static boolean sIsPresent = false;
    private static int sStatus = BatteryManager.BATTERY_STATUS_UNKNOWN;
    private static String sTechnology = "";
    private static double sTemperature = 0.00;
    private static int sVoltage = 0;
    private static double sPercent = 0.00;
    private static boolean sIsBatteryLow = false;

    private static BatteryReceiver sBatteryReceiver;

    private BatteryUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Instantiation is not allowed! Use static methods only!");
    }

    public static void registerBatteryReceiver(Context context) {
        if (sBatteryReceiver == null) {
            sBatteryReceiver = new BatteryReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        context.registerReceiver(sBatteryReceiver, intentFilter);
    }

    public static void unregisterBatteryReceiver(Context context) {
        context.unregisterReceiver(sBatteryReceiver);
    }

    public static int getLevel() {
        return sLevel;
    }

    public static int getScale() {
        return sScale;
    }

    public static int getHealth() {
        return sHealth;
    }

    public static String getHealthName() {
        String healthName;
        switch (getHealth()) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthName = "COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthName = "DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthName = "GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthName = "OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthName = "OVER_HEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthName = "UNSPECIFIED_FAILURE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                healthName = "UNKNOWN";
                break;
        }
        return healthName;
    }

    public static int getIconResId() {
        return sIconResId;
    }

    public static int getPlugType() {
        return sPlugType;
    }

    public static String getPlugTypeName() {
        String typeName;
        switch (getPlugType()) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                typeName = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                typeName = "USB";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                typeName = "WIRELESS";
                break;
            default:
                typeName = "UNKNOWN";
                break;
        }
        return typeName;
    }

    public static boolean isPresent() {
        return sIsPresent;
    }

    public static int getStatus() {
        return sStatus;
    }

    public static String getStatusName() {
        String statusName;
        switch (getStatus()) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                statusName = "CHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusName = "DISCHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                statusName = "FULL";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                statusName = "NOT_CHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
            default:
                statusName = "UNKNOWN";
                break;
        }
        return statusName;
    }

    public static String getTechnology() {
        return sTechnology;
    }

    public static double getTemperature() {
        return sTemperature;
    }

    public static int getVoltage() {
        return sVoltage;
    }

    public static double getPercent() {
        return sPercent;
    }

    public static boolean isBatteryLow() {
        return sIsBatteryLow;
    }

    public static class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Intent.ACTION_BATTERY_CHANGED:
                    sLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                    sScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                    sHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
                    sIconResId = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
                    sPlugType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                    sIsPresent = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
                    sStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
                    sTechnology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                    sTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) * 1.00 / 10.00;
                    sVoltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                    sPercent = (sLevel * 1.00) / sScale;
                    break;
                case Intent.ACTION_BATTERY_LOW:
                    sIsBatteryLow = true;
                    break;
                case Intent.ACTION_BATTERY_OKAY:
                    sIsBatteryLow = false;
                    break;
                default:
                    break;
            }
        }
    }
}
