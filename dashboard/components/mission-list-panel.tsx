import React, { useState, useEffect } from 'react'
import { Button } from "@/components/ui/button"
import { Switch } from "@/components/ui/switch"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Mission, MissionStatus } from "@/types/mission"
import apiClient from "@/lib/api-client"

interface MissionListPanelProps {
  onMissionSelect: (mission: Mission | null) => void;
}

export function MissionListPanel({ onMissionSelect }: MissionListPanelProps) {
  const [missions, setMissions] = useState<Mission[]>([])
  const [monitoredMission, setMonitoredMission] = useState<number | null>(null)

  useEffect(() => {
    fetchMissions()
  }, [])

  const fetchMissions = async () => {
    try {
      const response = await apiClient.get('/missions')
      setMissions(response.data._embedded.missions)
      console.log(response.data._embedded.missions)
    } catch (error) {
      console.error('Error fetching missions:', error)
    }
  }

  const handleToggleMonitor = (mission: Mission) => {
    const newMonitoredMission = monitoredMission === mission.id ? null : mission.id;
    setMonitoredMission(newMonitoredMission);
    onMissionSelect(newMonitoredMission ? mission : null);
  }

  return (
    <div className="w-64 bg-white shadow-md p-4 flex flex-col h-full overflow-hidden">
      <h3 className="text-lg font-semibold mb-4 text-[#6C63FF]">Missions</h3>
      <ScrollArea className="flex-grow overflow-y-auto">
        {missions.filter((mission) => mission.status === MissionStatus.PICKUP || mission.status === MissionStatus.ONROUTETOHOSPITAL).map((mission) => (
          <div key={mission.id} className="mb-4 p-2 border rounded">
            <div className="flex justify-between items-center mb-2">
              <span className="font-medium">Mission #{mission.id}</span>
              <Switch
                checked={monitoredMission === mission.id}
                onCheckedChange={() => handleToggleMonitor(mission)}
              />
            </div>
            <p className="text-sm text-gray-600">Status: {mission.status}</p>
            <p className="text-sm text-gray-600">Address: {mission.address}</p>
          </div>
        ))}
      </ScrollArea>
      <Button 
        onClick={fetchMissions} 
        className="mt-4 bg-[#6C63FF] hover:bg-[#6C63FF]/90"
      >
        Refresh Missions
      </Button>
    </div>
  )
}

