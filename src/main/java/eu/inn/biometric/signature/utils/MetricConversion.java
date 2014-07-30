package eu.inn.biometric.signature.utils;

import eu.inn.biometric.signature.device.MetricUnits;

public class MetricConversion {
	public final double MM_PER_INCH = 25.4;
	public final double PDFUNITS_PER_INCH = 72.0;

	private static double InchToPdfUnit(double inch) {
		return (inch * 72.0);
	}

	private static double MmToInch(double mm) {
		return (mm / 25.4);
	}

	public static double ToPdfUnits(double sourceValue, MetricUnits sourceUnit) throws Exception {
		switch (sourceUnit) {
		case PdfUnits:
			return sourceValue;
		case Millimeters:
			return InchToPdfUnit(MmToInch(sourceValue));
		case Inches:
			return InchToPdfUnit(sourceValue);
		case Points:
			return sourceValue;
		default:
		}
		throw new UnsupportedOperationException(sourceUnit.toString());
	}
}
