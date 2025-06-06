import type { UserPublicDto } from '../../../persistence/UserPublicDto';
import './user-table.css'
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

type TableData = {
  userData: UserPublicDto[];
  loading : boolean;
}

// userData = [] is default value, if the data hasnt loaded it wont "crash"
export default function UserTable({userData = [], loading} : TableData) {

  if (loading) return <p>Data is being loaded...</p>;

  return (
    <TableContainer component="div">
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell>UserId</TableCell>
            <TableCell>Username</TableCell>
            <TableCell>Firstname</TableCell>
            <TableCell>Lastname</TableCell>
            <TableCell>Age</TableCell>
            <TableCell>Is Fleetmanager</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {userData.map((user) => (
            <TableRow key={user.userId}>
              <TableCell>{user.userId}</TableCell>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.firstname}</TableCell>
              <TableCell>{user.surname}</TableCell>
              <TableCell>{user.age}</TableCell>
              <TableCell>{user.fleetManager ? 'Yes' : 'No'}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
