package COTATO_Combine_Networking.Networking.global.apiPayload.exception.handler;

import COTATO_Combine_Networking.Networking.global.apiPayload.code.status.ErrorStatus;

public class TempHandler extends RuntimeException {
	public TempHandler(ErrorStatus errorStatus) {
		super(errorStatus.getMessage());
	}
}
