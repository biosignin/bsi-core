package eu.inn.biometric.signature.isoiec19794.y2007;


public enum ChannelAttribute
{
    RESERVED_FUTURE_USE,
    LINEAR_COMPONENT_REMOVED,
    CONSTANT,
    STANDARD_DEVIATION,
    MEAN_CHANNEL_VALUE,
    MAXIMUM_CHANNEL_VALUE,
    MINIMUN_CHANNEL_VALUE,
    SCALING_VALUE;
    
    public static ChannelAttribute fromValue(int x) {
		switch (x) {
		case 0:
			return RESERVED_FUTURE_USE;
		case 1:
			return LINEAR_COMPONENT_REMOVED;
		case 2:
			return CONSTANT;
		case 3:
			return STANDARD_DEVIATION;
		case 4:
			return MEAN_CHANNEL_VALUE;
		case 5:
			return MAXIMUM_CHANNEL_VALUE;
		case 6:
			return MINIMUN_CHANNEL_VALUE;
		case 7:
			return SCALING_VALUE;
		
		}
		return null;
	}
}