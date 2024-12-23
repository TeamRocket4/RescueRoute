"use client";

import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { PlusCircle, Pencil } from "lucide-react";
import { Hospital } from "@/types/hospital";
import apiClient from "@/lib/api-client";

export default function HospitalsPage() {
  const [hospitals, setHospitals] = useState<Hospital[]>([]);
  const [newHospital, setNewHospital] = useState<Partial<Hospital>>({});
  const [editingHospital, setEditingHospital] = useState<Hospital | null>(null);
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false);
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false);
  const [MissionPosition, setMissionPosition] = useState<{
    lat: number;
    long: number;
  } | null>(null);

  useEffect(() => {
    fetchHospitals();
  }, []);

  const fetchHospitals = async () => {
    try {
      const response = await apiClient.get("/hospitals");
      setHospitals(response.data._embedded.hospitals);
    } catch (error) {
      console.error("Error fetching hospitals:", error);
    }
  };

  const handleInputChange = (
      e: React.ChangeEvent<HTMLInputElement>,
      isNewHospital: boolean
  ) => {
    const { name, value } = e.target;
    if (isNewHospital) {
      setNewHospital((prev) => ({ ...prev, [name]: value }));
    } else if (editingHospital) {
      setEditingHospital((prev) =>
          prev ? { ...prev, [name]: value } : null
      );
    }
  };

  const handleCreateMission = () => {
    if (!MissionPosition) {
      console.error("MissionPosition is null. Cannot create mission.");
      return; // Safely exit if MissionPosition is null
    }

    const newMission = {
      dispatcher: 1,
      latitude: MissionPosition?.lat ?? 0, // Use 0 or default value if undefined
      longitude: MissionPosition?.long ?? 0, // Use 0 or default value if undefined
    };

    console.log("Creating mission:", newMission);
    // Proceed with further logic (e.g., API call)
  };

  const handleAddHospital = async () => {
    try {
      await apiClient.post("/hospitals", newHospital);
      fetchHospitals();
      setNewHospital({});
      setIsAddDialogOpen(false);
    } catch (error) {
      console.error("Error adding hospital:", error);
    }
  };

  const handleUpdateHospital = async () => {
    if (editingHospital) {
      try {
        await apiClient.put(
            `/hospitals/${editingHospital.id}`,
            editingHospital
        );
        fetchHospitals();
        setEditingHospital(null);
        setIsEditDialogOpen(false);
      } catch (error) {
        console.error("Error updating hospital:", error);
      }
    }
  };

  const handleDeleteHospital = async (id: number) => {
    try {
      await apiClient.delete(`/hospitals/${id}`);
      fetchHospitals();
    } catch (error) {
      console.error("Error deleting hospital:", error);
    }
  };

  return (
      <div className="space-y-6">
        <div className="flex items-center justify-between">
          <h3 className="text-2xl font-medium text-[#F95738]">
            Hospital Management
          </h3>
          <Dialog open={isAddDialogOpen} onOpenChange={setIsAddDialogOpen}>
            <DialogTrigger asChild>
              <Button className="bg-[#F95738] hover:bg-[#F95738]/90">
                <PlusCircle className="mr-2 h-4 w-4" />
                Add Hospital
              </Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Add New Hospital</DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div>
                  <Label htmlFor="name">Hospital Name</Label>
                  <Input
                      id="name"
                      name="name"
                      value={newHospital.name || ""}
                      onChange={(e) => handleInputChange(e, true)}
                  />
                </div>
                <div>
                  <Label htmlFor="address">Address</Label>
                  <Input
                      id="address"
                      name="address"
                      value={newHospital.address || ""}
                      onChange={(e) => handleInputChange(e, true)}
                  />
                </div>
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="latitude">Latitude</Label>
                    <Input
                        id="latitude"
                        name="latitude"
                        type="number"
                        value={newHospital.latitude || ""}
                        onChange={(e) => handleInputChange(e, true)}
                    />
                  </div>
                  <div>
                    <Label htmlFor="longitude">Longitude</Label>
                    <Input
                        id="longitude"
                        name="longitude"
                        type="number"
                        value={newHospital.longitude || ""}
                        onChange={(e) => handleInputChange(e, true)}
                    />
                  </div>
                </div>
              </div>
              <Button
                  id="AddHospitalButton"
                  onClick={handleAddHospital}
                  className="bg-[#F95738] hover:bg-[#F95738]/90"
              >
                Add Hospital
              </Button>
            </DialogContent>
          </Dialog>
        </div>
        <div className="rounded-md border">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead>Address</TableHead>
                <TableHead>Latitude</TableHead>
                <TableHead>Longitude</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {hospitals.map((hospital) => (
                  <TableRow key={hospital.id}>
                    <TableCell>{hospital.name}</TableCell>
                    <TableCell>{hospital.address}</TableCell>
                    <TableCell>{hospital.latitude}</TableCell>
                    <TableCell>{hospital.longitude}</TableCell>
                    <TableCell>
                      <div className="flex space-x-2">
                        <Dialog
                            open={isEditDialogOpen}
                            onOpenChange={setIsEditDialogOpen}
                        >
                          <DialogTrigger asChild>
                            <Button
                                id="editButton"
                                variant="outline"
                                size="sm"
                                onClick={() => setEditingHospital(hospital)}
                            >
                              <Pencil className="h-4 w-4" />
                            </Button>
                          </DialogTrigger>
                          <DialogContent>
                            <DialogHeader>
                              <DialogTitle>Edit Hospital</DialogTitle>
                            </DialogHeader>
                            <div className="grid gap-4 py-4">
                              <div>
                                <Label htmlFor="editName">Hospital Name</Label>
                                <Input
                                    id="editName"
                                    name="name"
                                    value={editingHospital?.name || ""}
                                    onChange={(e) => handleInputChange(e, false)}
                                />
                              </div>
                              <div>
                                <Label htmlFor="editAddress">Address</Label>
                                <Input
                                    id="editAddress"
                                    name="address"
                                    value={editingHospital?.address || ""}
                                    onChange={(e) => handleInputChange(e, false)}
                                />
                              </div>
                              <div className="grid grid-cols-2 gap-4">
                                <div>
                                  <Label htmlFor="editLatitude">Latitude</Label>
                                  <Input
                                      id="editLatitude"
                                      name="latitude"
                                      type="number"
                                      value={editingHospital?.latitude || ""}
                                      onChange={(e) => handleInputChange(e, false)}
                                  />
                                </div>
                                <div>
                                  <Label htmlFor="editLongitude">Longitude</Label>
                                  <Input
                                      id="editLongitude"
                                      name="longitude"
                                      type="number"
                                      value={editingHospital?.longitude || ""}
                                      onChange={(e) => handleInputChange(e, false)}
                                  />
                                </div>
                              </div>
                            </div>
                            <Button
                                id="UpdateButton"
                                onClick={handleUpdateHospital}
                                className="bg-[#F95738] hover:bg-[#F95738]/90"
                            >
                              Update Hospital
                            </Button>
                          </DialogContent>
                        </Dialog>
                        <Button
                            variant="destructive"
                            size="sm"
                            onClick={() => handleDeleteHospital(hospital.id)}
                        >
                          Delete
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
      </div>
  );
}
