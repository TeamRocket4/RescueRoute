"use client"

import { useCallback, useEffect, useMemo, useState } from "react"
import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { User, Role, Status } from "@/types/user"
import { Hospital } from "@/types/hospital"
import { Position } from "@/types/position"
import { Mission, MissionStatus } from "@/types/mission"
import { GoogleMap, LoadScript, Marker, DirectionsRenderer, DirectionsService } from "@react-google-maps/api";

import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

import apiClient from "@/lib/api-client"
import { MissionListPanel } from "@/components/mission-list-panel"




export default function MapPage() {
  const [selectedOption, setSelectedOption] = useState<string | null>(null)
  const [isDialogOpen, setIsDialogOpen] = useState(false)

  const [newMission, setNewMission] = useState<Partial<Mission>>({
    status: MissionStatus.PICKUP
  })

  const [hospitals, setHospitals] = useState<Hospital[]>([])
  const [drivers, setDrivers] = useState<User[]>([])


  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [positions, setPositions] = useState<Position[]>([]);
  const [position, setPosition] = useState<Partial<Position>>({});
  const [selectedMission, setSelectedMission] = useState<Mission | null>(null);

  const [MissionPosition, setMissionPossition] = useState(null);

  const [SelectedDriver, setSelectedDriver] = useState<User | null>(null);
  const [SelectedHospital, setSelectedHospital] = useState<Hospital | null>(null);

  const [directions, setDirections] = useState<google.maps.DirectionsResult | null>(null);


  useEffect(() => {
    fetchHospitals();
    fetchDrivers();
    const socket = new SockJS('http://localhost:8080/position');
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

  const fetchDrivers = async () => {
    try {
      const response = await apiClient.get('/users')
      setDrivers(response.data._embedded.users)
    } catch (error) {
      console.error('Error fetching drivers:', error)
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
    console.log(position)
    setSelectedOption(option)
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewMission({ ...newMission, [e.target.name]: e.target.value })
  }

  const handleSelectChange = (name: string, value: string) => {
    setNewMission({ ...newMission, [name]: value })
  }

  const handleCreateMission = () => {
    if (MissionPosition.lat != null && MissionPosition.long != null) {
      newMission.dispatcher = 1;
      newMission.latitude = MissionPosition.lat;
      newMission.longitude = MissionPosition.long;

      console.log("Creating mission:", newMission)

      if (stompClient && stompClient.connected) {
        stompClient.publish({ destination: '/app/driver/1', body: JSON.stringify(newMission) });
      }

      setIsDialogOpen(false)
    }
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
      lat: ev.latLng.lat(),
      long: ev.latLng.lng()
    })
  }

  const handleMissionSelect = async (mission: Mission | null) => {
    if (mission != null) {
      console.log(mission);

      try {
        const driverResponse = await apiClient.get(mission._links.driver.href);
        setSelectedDriver(driverResponse.data);

        const hospitalResponse = await apiClient.get(mission._links.hospital.href);
        setSelectedHospital(hospitalResponse.data);



        // Work with driverResponse.data directly for positions
        const position = positions.find((pos) => pos.id === driverResponse.data?.id);
        if (position) {
          console.log("kat")
          setPosition(position);
        }

        // Logs for debugging
        console.log(driverResponse.data);
        console.log(hospitalResponse.data);

        setSelectedMission(mission);
      } catch (error) {
        console.log(error.message);
      }
    } else {
      setSelectedMission(null);
    }
  };



  const directionsCallback = useCallback((
    result: google.maps.DirectionsResult | null,
    status: google.maps.DirectionsStatus
  ) => {
    if (status === 'OK') {
      setDirections(result);
    }
  }, []);



  const renderSelectedMission = useMemo(() => {
    if (!selectedMission) return null;

    return (
      <>
        {selectedMission.status === MissionStatus.PICKUP && (
          <>
            <Marker
              icon={{ url: "/ambulance.png" }}
              position={{
                lat: Number(position.latitude),
                lng: Number(position.longitude)
              }}
            />
            <Marker
              position={{
                lat: selectedMission.latitude,
                lng: selectedMission.longitude
              }}
            />
            <DirectionsService
              options={{
                destination: {
                  lat: Number(position.latitude),
                  lng: Number(position.longitude)
                },
                origin: {
                  lat: selectedMission.latitude,
                  lng: selectedMission.longitude
                },
                travelMode: google.maps.TravelMode.DRIVING
              }}
              callback={directionsCallback}
            />
          </>
        )}
        {selectedMission.status === MissionStatus.ONROUTETOHOSPITAL && (
          <>
            <Marker
              icon={{ url: "/ambulance.png" }}
              position={{
                lat: Number(position.latitude),
                lng: Number(position.longitude)
              }}
            />
            <Marker
              icon={{ url: "/hospital.png" }}
              position={{
                lat: Number(SelectedHospital?.latitude),
                lng: Number(SelectedHospital?.longitude)
              }}
            />
            <DirectionsService
              options={{
                destination: {
                  lat: Number(position.latitude),
                  lng: Number(position.longitude)
                },
                origin: {
                  lat: Number(SelectedHospital?.latitude),
                  lng: Number(SelectedHospital?.longitude)
                },
                travelMode: google.maps.TravelMode.DRIVING
              }}
              callback={directionsCallback}
            />
          </>
        )}
      </>
    );
  }, [selectedMission, position, SelectedHospital, MissionStatus, directionsCallback]);


  const renderDefaultMarkers = useMemo(() => {
    if (selectedMission) return null;

    return (
      <>
        {positions.map((pos, index) => (
          <Marker
            key={`position-${index}`}
            icon={{ url: "/ambulance.png" }}
            position={{
              lat: Number(pos.latitude),
              lng: Number(pos.longitude),
            }}
          />
        ))}
        {hospitals.map((hospital, index) => (
          <Marker
            key={`hospital-${index}`}
            icon={{ url: "/hospital.png" }}
            position={{
              lat: Number(hospital.latitude),
              lng: Number(hospital.longitude),
            }}
          />
        ))}
      </>
    );
  }, [positions, hospitals, selectedMission]);



  return (
    <div className="space-y-6 flex h-[calc(100vh-6rem)]">
      <div className="flex-grow space-y-6">
        <h3 className="text-2xl font-medium text-[#6C63FF]">Map</h3>
        <div className="aspect-video rounded-lg bg-gray-200 flex items-center justify-center">
          {/* <div className="text-gray-500" style={{width:"100%", height:"100%"}}> */}

          <LoadScript googleMapsApiKey={process.env.NEXT_PUBLIC_MAPS_KEY!}>
            <GoogleMap
              mapContainerStyle={containerStyle}
              center={MissionPosition || center}
              zoom={8}
              onClick={(ev) => { updateMissionPosition(ev) }}
            >
              {MissionPosition && <Marker
                position={{
                  lat: MissionPosition.lat,
                  lng: MissionPosition.long,
                }}
              />}


              {selectedMission ? (
                <>
                  {renderSelectedMission}
                </>) :
                <>
                  {renderDefaultMarkers}
                </>}

              {directions && (
                <DirectionsRenderer
                  options={{
                    directions,
                  }}
                />
              )}




            </GoogleMap>
          </LoadScript>

          {/* </div> */}
        </div>
        <div className="flex space-x-4">
          <Button
            onClick={() => handleButtonClick("Show Hospitals")}
            className="bg-[#6C63FF] hover:bg-[#6C63FF]/90"
          >
            Show Hospitals
          </Button>
          <Button
            onClick={() => handleButtonClick("Show Drivers")}
            className="bg-[#6C63FF] hover:bg-[#6C63FF]/90"
          >
            Show Drivers
          </Button>
          <Button
            onClick={() => handleButtonClick("Optimize Routes")}
            className="bg-[#6C63FF] hover:bg-[#6C63FF]/90"
          >
            Optimize Routes
          </Button>
          <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
            <DialogTrigger asChild>
              <Button className="bg-[#6C63FF] hover:bg-[#6C63FF]/90">Create Mission</Button>
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
                      {drivers.map((driver) => (
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
              <Button onClick={handleCreateMission} className="bg-[#6C63FF] hover:bg-[#6C63FF]/90">
                Create Mission
              </Button>
            </DialogContent>
          </Dialog>
        </div>
        {selectedOption && (
          <p className="mt-4 p-4 bg-gray-100 rounded-md">Selected option: {selectedOption}</p>
        )}
        {selectedMission && (
          <div className="mt-4 p-4 bg-gray-100 rounded-md">
            <h4 className="text-lg font-semibold mb-2">Selected Mission</h4>
            <p>ID: {selectedMission.id}</p>
            <p>Status: {selectedMission.status}</p>
            <p>Address: {selectedMission.address}</p>
            {/* Add more mission details as needed */}
          </div>
        )}
      </div>
      <MissionListPanel onMissionSelect={handleMissionSelect} />
    </div>
  )
}

