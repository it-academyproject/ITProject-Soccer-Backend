package com.itacademy.soccer.gameEngine.interfaces;

import java.util.Date;

public interface IGameEngine {

	// Planificar/Programar el partido con id "matchId" para ser ejecutado un día y una hora
	// determinada (registrada en el campo date del partido)
	public void scheduleMatch(Long matchId);
	
	// Ejecutar y generar eventos del partido con id "matchId" 
	// Este método es invocado por scheduleMatch cuando coincide la hora real con la hora del partido
	public void playMatch(Long matchId);
	
}
