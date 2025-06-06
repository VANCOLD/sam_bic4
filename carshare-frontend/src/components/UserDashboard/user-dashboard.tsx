import { Card, Divider } from "@mui/material";
import UserRegister from "./UserRegister/user-register";
import UserTable from "./UserTable/user-table";
import type { UserPublicDto } from "../../persistence/UserPublicDto";
import { useEffect, useState } from "react";
import "./user-dashboard.css"

export default function UserDashboard() {
  const [users, setUsers] = useState<UserPublicDto[]>([]);
  const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchUserData();
    }, []);

    const fetchUserData = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/users', {
                headers: {
                    'Authorization': 'Bearer 1',
                    'Content-Type': 'application/json',
                },
            });
            const data: UserPublicDto[] = await response.json();
            setUsers(data);
            setLoading(false);
        } catch (error) {
            console.error(error);
            setLoading(false);
        }
    };

  return (
    <Card variant="outlined">
      <UserTable userData={users} loading={loading} />
      <Divider sx={{ my: 5  }} />
      <UserRegister onSuccess={fetchUserData}/>
    </Card>
  );
}