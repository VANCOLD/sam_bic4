export type VehicleDto = {

    vehicleId: number;
    longitude: number;
    latitude: number;
    currentTimestamp: Date;
    isOccupied: boolean;
    driverId: number;
    distanceSinceLastUpdate: number;
    timeSinceLastUpdate: number;
    priority: string;
    emergencyDescription: string;
}
    
