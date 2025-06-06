import type { VehicleDto } from '../../../persistence/VehicleDto';
import './vehicle-table.css'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

type TableData = {
  vehicleData: VehicleDto[];
  loading : boolean;
}

// userData = [] is default value, if the data hasnt loaded it wont "crash"
export default function VehicleTable({vehicleData = [], loading} : TableData) {

  if (loading) return <p>Data is being loaded...</p>;

  return (
    <TableContainer component="div">
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>Vehicle ID</TableCell>
            <TableCell>longitude</TableCell>
            <TableCell>latitude</TableCell>
            <TableCell>Current Timestamp</TableCell>
            <TableCell>Is Occupied</TableCell>
            <TableCell>Driver Id</TableCell>
            <TableCell>DSLU</TableCell>
            <TableCell>TSLU*</TableCell>
            <TableCell>Priority</TableCell>
            <TableCell>Emergency Discription</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {vehicleData.map((vehicle) => (
            <TableRow key={vehicle.vehicleId}>
              <TableCell>{vehicle.vehicleId}</TableCell>
              <TableCell>{vehicle.longitude}</TableCell>
              <TableCell>{vehicle.latitude}</TableCell>
              <TableCell>{vehicle.currentTimestamp.toString()}</TableCell>
              <TableCell>{vehicle.isOccupied ? 'Yes' : 'No'}</TableCell>
              <TableCell>{vehicle.driverId}</TableCell>
              <TableCell>{vehicle.distanceSinceLastUpdate}</TableCell>
              <TableCell>{vehicle.timeSinceLastUpdate}</TableCell>
              <TableCell>{vehicle.priority}</TableCell>
              <TableCell>{vehicle.emergencyDescription}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
