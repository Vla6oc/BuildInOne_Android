package com.example.cp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;


public class Ruler {
    private static Context context;
    Ruler(Context current) {
        this.context = current;
    }

    public static String compute(List<Point> points1, List<Point> points, double scale, int inputUnitIndex, int outputUnitIndex) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (points.size() == 2)
        {
            Point ref1 = points1.get(0);
            Point ref2 = points1.get(1);
            double reference = getDistance(ref1, ref2);
            double measurement;
            Point m1 = points.get(0);
            Point m2 = points.get(1);
            double measurement0 = getDistance(m1, m2);
            double a = (measurement0 * scale) / reference;
            measurement = a;
            measurement = convertUnits(inputUnitIndex, reference, outputUnitIndex, measurement);
            double s = (Math.pow(measurement, 2) * 3.14)/4;
            return  context.getResources().getString(R.string.len) + " " + decimalFormat.format(measurement) + "\n" + context.getResources().getString(R.string.s_o) + " " + decimalFormat.format(s);
        }
        if (points.size() == 3)
        {
            Point ref1 = points1.get(0);
            Point ref2 = points1.get(1);
            double reference = getDistance(ref1, ref2);
            double measurement;
            Point m1 = points.get(0);
            Point m2 = points.get(1);
            Point m3 = points.get(2);
            double measurement0 = getDistance(m1, m2);
            double measurement1 = getDistance(m2, m3);
            double measurement2 = getDistance(m3, m1);
            double a = (measurement0 * scale) / reference;
            double b = (measurement1 * scale) / reference;
            double c = (measurement2 * scale) / reference;
            double P =  a + b + c; //Get the actual distance
            double p = P/2;
            measurement = Math.sqrt(p*(p - a)*(p - b)*(p - c));
            measurement = convertUnitsS(inputUnitIndex, reference, outputUnitIndex, measurement);
            return context.getResources().getString(R.string.s_t)  + " " +  decimalFormat.format(measurement);

        }
        if (points.size() == 4)
        {
            Point ref1 = points1.get(0);
            Point ref2 = points1.get(1);
            double reference = getDistance(ref1, ref2);
            double measurement;
            Point m1 = points.get(0);
            Point m2 = points.get(1);
            Point m3 = points.get(2);
            Point m4 = points.get(3);
            double measurement0 = getDistance(m1, m2);
            double measurement1 = getDistance(m2, m3);
            double measurement2 = getDistance(m3, m4);
            double measurement3 = getDistance(m4, m1);
            double measurement4 = getDistance(m1, m3);
            double measurement5 = getDistance(m2, m4);
            double a = (measurement0 * scale) / reference;
            double b = (measurement1 * scale) / reference;
            double c = (measurement2 * scale) / reference;
            double d = (measurement3 * scale) / reference;
            double d1 = ((measurement4 * scale) / reference)/2;
            double d2 = ((measurement5 * scale) / reference)/2;
            double d12 = Math.pow(d1 , 2);
            double d22 = Math.pow(d2 , 2);
            double b2 = Math.pow(b, 2);
            double cos = 0-((b2 - d12 - d22)/(2*d1*d2));
            double sin = Math.sqrt(1 - (Math.pow(cos , 2)));
            double s = d1*d2*2*sin;
            measurement = s;
            measurement = convertUnitsS(inputUnitIndex, reference, outputUnitIndex, measurement);

            return context.getResources().getString(R.string.s_p)  + " " +  decimalFormat.format(measurement);
        }
        else {
            return null;
        }
    }

    private static double getDistance(Point p1, Point p2) {
        double x = Math.pow(p2.x - p1.x, 2);
        double y = Math.pow(p2.y - p1.y, 2);
        return Math.sqrt(x + y);
    }

    private static double convertUnits(int refUnit, double reference, int meaUnit, double measurement) {
        if (refUnit == meaUnit)
            return measurement;

        measurement = toMeters(measurement, refUnit);
        switch (meaUnit) {
            case 0:
                return measurement;
            case 1:
                return Utils.metersToCentimeters(measurement);
            case 2:
                return Utils.metersToMillimeters(measurement);
            case 3:
                return Utils.metersToInch(measurement);
            case 4:
                return Utils.metersToFeet(measurement);
            case 5:
                return Utils.metersToYards(measurement);
            default:
                return -1;
        }
    }

    private static double toMeters(double measurement, int refUnit) {
        switch (refUnit) {
            case 0:
                return measurement;
            case 1:
                return Utils.centimetersToMeters(measurement);
            case 2:
                return Utils.millimetersToMeters(measurement);
            case 3:
                return Utils.inchesToMeters(measurement);
            case 4:
                return Utils.yardsToMeters(measurement);
            default:
                return -1;
        }
    }


    private static double convertUnitsS(int refUnit, double reference, int meaUnit, double measurement) {
        if (refUnit == meaUnit)
            return measurement;

        measurement = toMetersS(measurement, refUnit);
        switch (meaUnit) {
            case 0:
                return measurement;
            case 1:
                return Utils.metersToCentimetersS(measurement);
            case 2:
                return Utils.metersToMillimetersS(measurement);
            case 3:
                return Utils.metersToInchS(measurement);
            case 4:
                return Utils.metersToFeetS(measurement);
            case 5:
                return Utils.metersToYardsS(measurement);
            default:
                return -1;
        }
    }

    private static double toMetersS(double measurement, int refUnit) {
        switch (refUnit) {
            case 0:
                return measurement;
            case 1:
                return Utils.centimetersToMetersS(measurement);
            case 2:
                return Utils.millimetersToMetersS(measurement);
            case 3:
                return Utils.inchesToMetersS(measurement);
            case 4:
                return Utils.yardsToMetersS(measurement);
            default:
                return -1;
        }
    }
}
