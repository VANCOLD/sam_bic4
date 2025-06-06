import { Card, Divider } from "@mui/material";
import VehicleRegister from "./VehicleRegister/vehicle-register";
import VehicleTable from "./VehicleTable/vehicle-table";
import { useEffect, useState } from "react";
import "./vehicle-dashboard.css"
import type { VehicleDto } from "../../persistence/VehicleDto";
import type { PriorityDto } from "../../persistence/PriorityDto";

export default function UserDashboard() {
  const [vehicles, setVehicles] = useState<VehicleDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [priorities, setPriorities] = useState<PriorityDto[]>([])

    useEffect(() => {
        fetchPriorityData();
        fetchVehicleData();
    }, []);

    const fetchPriorityData = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/vehicles/priorities', {
                headers: {
                    'Authorization': 'Bearer 1',
                    'Content-Type': 'application/json',
                },
            });
            const data: PriorityDto[] = await response.json();
            setPriorities(data);
        } catch (error) {
            console.error(error);
        }
    };

    const fetchVehicleData = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/vehicles', {
                headers: {
                    'Authorization': 'Bearer 1',
                    'Content-Type': 'application/json',
                },
            });
            const data: VehicleDto[] = await response.json();
            setVehicles(data);
            setLoading(false);
        } catch (error) {
            console.error(error);
            setLoading(false);
        }
    };

  return (
    <Card variant="outlined">
      <VehicleTable vehicleData={vehicles} loading={loading} />
      <Divider sx={{ my: 5  }} />
      <VehicleRegister onSuccess={fetchVehicleData} priorityOptions={priorities}/>
    </Card>
  );
}