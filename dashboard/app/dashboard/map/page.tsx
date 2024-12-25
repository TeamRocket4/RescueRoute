"use client"

import { useEffect, useState } from "react"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { User, Role, Status } from "@/types/user"
import { Hospital } from "@/types/hospital"
import { Position } from "@/types/position"
import { Mission, MissionStatus } from "@/types/mission"
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";

import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

import apiClient from "@/lib/api-client"
import axios from "axios"

// Mock data for demonstration
const mockDrivers: User[] = [
  { id: 1, firstName: "John", lastName: "Doe", email: "john@example.com", password: "", role: Role.DRIVER, birthDate: "1990-01-01", status: Status.STANDBY },
  { id: 2, firstName: "Jane", lastName: "Smith", email: "jane@example.com", password: "", role: Role.DRIVER, birthDate: "1992-05-15", status: Status.STANDBY },
]

const mockHospitals: Hospital[] = [
  { id: 1, name: "Central Hospital", address: "123 Main St", latitude: 40.7128, longitude: -74.0060 },
  { id: 2, name: "Community Health Center", address: "456 Oak Ave", latitude: 34.0522, longitude: -118.2437 },
]





export default function MapPage() {
  const [selectedOption, setSelectedOption] = useState<string | null>(null)
  const [isDialogOpen, setIsDialogOpen] = useState(false)

  const [newMission, setNewMission] = useState<Partial<Mission>>({
    status: MissionStatus.ASSIGNED
  })

  const [hospitals, setHospitals] = useState<Hospital[]>([])


  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [positions, setPositions] = useState<Position[]>([]);
  const [position, setPosition] = useState<Partial<Position>>({});

  const [MissionPosition, setMissionPossition] = useState(null);

  useEffect(() => {
    // Connect to the WebSocket server
    fetchHospitals();
    const socket = new SockJS('http://65.20.105.247:8080/position');
    const client = new Client({
      webSocketFactory: () => socket,  // Provide SockJS as the WebSocket factory
    });

    client.onConnect = () => {
      console.log('Connected to WebSocket');

      // Subscribe to the topic
      client.subscribe('/map/positions', (message) => {
        const receivedMessage: Position = JSON.parse(message.body);
        console.log(receivedMessage)
        setPositions((prevMessages) => [...prevMessages, receivedMessage]);
      });
    };

    client.activate();

    setStompClient(client);

    // Cleanup connection on component unmount
    return () => {
      if (client.connected) {
        client.deactivate();
        console.log('Disconnected from WebSocket');
      }
    };
  }, []);


  const fetchHospitals = async () => {
    try {
      const response = await apiClient.get('/hospitals')
      setHospitals(response.data._embedded.hospitals)
    } catch (error) {
      console.error('Error fetching hospitals:', error)
    }
  }




  const sendMessage = () => {
    if (stompClient && stompClient.connected) {
      const messageObject: Position = { id: 2, latitude: 31.6275884, longitude: -7.9833476 };
      stompClient.publish({ destination: '/app/position', body: JSON.stringify(messageObject) });
      const messageObject2: Position = { id: 3, latitude: 31.6545535, longitude: -8.0050109 };
      stompClient.publish({ destination: '/app/position', body: JSON.stringify(messageObject2) });
      const messageObject3: Position = { id: 4, latitude: 31.6168399, longitude: -8.0446858 };
      stompClient.publish({ destination: '/app/position', body: JSON.stringify(messageObject3) });
      console.log(positions);
    }
  };


  const handleButtonClick = (option: string) => {
    setSelectedOption(option)
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewMission({ ...newMission, [e.target.name]: e.target.value })
  }

  const handleSelectChange = (name: string, value: string) => {
    setNewMission({ ...newMission, [name]: value })
  }

  const handleCreateMission = () => {
    newMission.dispatcher = 1;
    newMission.latitude = MissionPosition.lat;
    newMission.longitude = MissionPosition.long;

    console.log("Creating mission:", newMission)

    if (stompClient && stompClient.connected) {
      stompClient.publish({ destination: '/app/position/1', body: JSON.stringify(newMission) });
    }
    
    setIsDialogOpen(false)
    // Here you would typically send this data to your backend
  }

  const containerStyle = {
    width: "100%",
    height: "100%",
  };

  const center = {
    lat: 31.634595, // Latitude for San Francisco (example)
    lng: -8.0902538, // Longitude for San Francisco (example)
  };

  const updateMissionPosition = (ev) => {
    setMissionPossition({
      lat : ev.latLng.lat(),
      long : ev.latLng.lng()
    })
  }

  return (
    <div className="space-y-6">
      <h3 className="text-2xl font-medium text-[#F95738]">Map</h3>
      <div className="aspect-video rounded-lg bg-gray-200 flex items-center justify-center relative">
        {/* <div className="text-gray-500" style={{width:"100%", height:"100%"}}> */}

        <LoadScript googleMapsApiKey="">
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={MissionPosition || center}
            zoom={8}
            onClick={(ev) => {updateMissionPosition(ev)}}
          >
            {MissionPosition && <Marker
              position={{
                lat: MissionPosition.lat,
                lng: MissionPosition.long,
              }} 
              />}

            {positions.map((position, index) => (
              <Marker
                key={index}

                icon={
                  {
                    url: "/ambulance.png"
                  }
                }
                position={{
                  lat: Number(position.latitude),
                  lng: Number(position.longitude),
                }}
              />
            ))}
            {hospitals.map((hospital, index) => (
              <Marker
                key={index}

                icon={
                  {
                    url: "/hospital.png"
                  }
                }
                position={{
                  lat: Number(hospital.latitude),
                  lng: Number(hospital.longitude),
                }}
              />
            ))}
            {/* <Marker icon={{
                  url:'@/public/ambulance.png',
                  scaledSize: new window.google.maps.Size(50 * 7, 50 * 7),
                }} position={center} /> */}
          </GoogleMap>
        </LoadScript>

        {/* </div> */}
      </div>
      <div className="flex space-x-4">
        <Button
          onClick={() => sendMessage()}
          className="bg-[#F95738] hover:bg-[#F95738]/90"
        >
          Show Hospitals
        </Button>
        <Button
          onClick={() => handleButtonClick("Show Drivers")}
          className="bg-[#F95738] hover:bg-[#F95738]/90"
        >
          Show Drivers
        </Button>
        <Button
          onClick={() => handleButtonClick("Optimize Routes")}
          className="bg-[#F95738] hover:bg-[#F95738]/90"
        >
          Optimize Routes
        </Button>
        <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
          <DialogTrigger asChild>
            <Button className="bg-[#F95738] hover:bg-[#F95738]/90">Create Mission</Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Create New Mission</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              
              <div>
                <Label htmlFor="driver">Driver</Label>
                <Select onValueChange={(value) => handleSelectChange("driver", value)}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a driver" />
                  </SelectTrigger>
                  <SelectContent>
                    {mockDrivers.map((driver) => (
                      <SelectItem key={driver.id} value={driver.id.toString()}>
                        {driver.firstName} {driver.lastName}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div>
                <Label htmlFor="hospital">Hospital</Label>
                <Select onValueChange={(value) => handleSelectChange("hospital", value)}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a hospital" />
                  </SelectTrigger>
                  <SelectContent>
                    {hospitals.map((hospital) => (
                      <SelectItem key={hospital.id} value={hospital.id.toString()}>
                        {hospital.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
            </div>
            <Button onClick={handleCreateMission} className="bg-[#F95738] hover:bg-[#F95738]/90">
              Create Mission
            </Button>
          </DialogContent>
        </Dialog>
      </div>
      {selectedOption && (
        <p className="mt-4 p-4 bg-gray-100 rounded-md">Selected option: {selectedOption}</p>
      )}
    </div>
  )
}

