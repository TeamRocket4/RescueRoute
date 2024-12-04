"use client"

import { useState, useEffect } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { PlusCircle, Pencil } from 'lucide-react'
import { User, Role, Status } from "@/types/user"
import apiClient from "@/lib/api-client"

export default function UsersPage() {
  const [users, setUsers] = useState<User[]>([])
  const [newUser, setNewUser] = useState<Partial<User>>({
    role: Role.DRIVER,
    status: Status.STANDBY,
  })
  const [editingUser, setEditingUser] = useState<User | null>(null)
  const [isAddDialogOpen, setIsAddDialogOpen] = useState(false)
  const [isEditDialogOpen, setIsEditDialogOpen] = useState(false)

  useEffect(() => {
    fetchUsers()
  }, [])

  const fetchUsers = async () => {
    try {
      const response = await apiClient.get('/users')
      setUsers(response.data._embedded.users)
    } catch (error) {
      console.error('Error fetching users:', error)
    }
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>, isNewUser: boolean) => {
    const { name, value } = e.target
    if (isNewUser) {
      setNewUser(prev => ({ ...prev, [name]: value }))
    } else if (editingUser) {
      setEditingUser(prev => ({ ...prev, [name]: value }))
    }
  }

  const handleSelectChange = (value: string, field: string, isNewUser: boolean) => {
    if (isNewUser) {
      setNewUser(prev => ({ ...prev, [field]: value }))
    } else if (editingUser) {
      setEditingUser(prev => ({ ...prev, [field]: value }))
    }
  }

  const handleAddUser = async () => {
    try {
      await apiClient.post('/users', newUser)
      fetchUsers()
      setNewUser({
        role: Role.DRIVER,
        status: Status.STANDBY,
      })
      setIsAddDialogOpen(false)
    } catch (error) {
      console.error('Error adding user:', error)
    }
  }

  const handleUpdateUser = async () => {
    if (editingUser) {
      try {
        await apiClient.put(`/users/${editingUser.id}`, editingUser)
        fetchUsers()
        setEditingUser(null)
        setIsEditDialogOpen(false)
      } catch (error) {
        console.error('Error updating user:', error)
      }
    }
  }

  const handleDeleteUser = async (id: number) => {
    try {
      console.log(`/users/${id}`)
      await apiClient.delete(`/users/${id}`)
      fetchUsers()
    } catch (error) {
      console.error('Error deleting user:', error)
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h3 className="text-2xl font-medium text-[#F95738]">User Management</h3>
        <Dialog open={isAddDialogOpen} onOpenChange={setIsAddDialogOpen}>
          <DialogTrigger asChild>
            <Button className="bg-[#F95738] hover:bg-[#F95738]/90">
              <PlusCircle className="mr-2 h-4 w-4" />
              Add User
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Add New User</DialogTitle>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="firstName">First Name</Label>
                  <Input id="firstName" name="firstName" value={newUser.firstName || ''} onChange={(e) => handleInputChange(e, true)} />
                </div>
                <div>
                  <Label htmlFor="lastName">Last Name</Label>
                  <Input id="lastName" name="lastName" value={newUser.lastName || ''} onChange={(e) => handleInputChange(e, true)} />
                </div>
              </div>
              <div>
                <Label htmlFor="email">Email</Label>
                <Input id="email" name="email" type="email" value={newUser.email || ''} onChange={(e) => handleInputChange(e, true)} />
              </div>
              <div>
                <Label htmlFor="password">Password</Label>
                <Input id="password" name="password" type="password" value={newUser.password || ''} onChange={(e) => handleInputChange(e, true)} />
              </div>
              <div>
                <Label htmlFor="role">Role</Label>
                <Select onValueChange={(value) => handleSelectChange(value, 'role', true)} value={newUser.role}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a role" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value={Role.DISPATCHER}>Dispatcher</SelectItem>
                    <SelectItem value={Role.DRIVER}>Driver</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div>
                <Label htmlFor="status">Status</Label>
                <Select onValueChange={(value) => handleSelectChange(value, 'status', true)} value={newUser.status}>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a status" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value={Status.STANDBY}>Standby</SelectItem>
                    <SelectItem value={Status.BUSY}>Busy</SelectItem>
                    <SelectItem value={Status.OUTOFSERVICE}>Out of Service</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div>
                <Label htmlFor="birthDate">Birth Date</Label>
                <Input id="birthDate" name="birthDate" type="date" value={newUser.birthDate || ''} onChange={(e) => handleInputChange(e, true)} />
              </div>
            </div>
            <Button onClick={handleAddUser} className="bg-[#F95738] hover:bg-[#F95738]/90">
              Add User
            </Button>
          </DialogContent>
        </Dialog>
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>First Name</TableHead>
              <TableHead>Last Name</TableHead>
              <TableHead>Email</TableHead>
              <TableHead>Role</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Birth Date</TableHead>
              <TableHead>Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {users.map((user) => (
              <TableRow key={user.id}>
                <TableCell>{user.firstName}</TableCell>
                <TableCell>{user.lastName}</TableCell>
                <TableCell>{user.email}</TableCell>
                <TableCell>{user.role}</TableCell>
                <TableCell>{user.status}</TableCell>
                <TableCell>{user.birthDate}</TableCell>
                <TableCell>
                  <div className="flex space-x-2">
                    <Dialog open={isEditDialogOpen} onOpenChange={setIsEditDialogOpen}>
                      <DialogTrigger asChild>
                        <Button variant="outline" size="sm" onClick={() => setEditingUser(user)}>
                          <Pencil className="h-4 w-4" />
                        </Button>
                      </DialogTrigger>
                      <DialogContent>
                        <DialogHeader>
                          <DialogTitle>Edit User</DialogTitle>
                        </DialogHeader>
                        <div className="grid gap-4 py-4">
                          <div className="grid grid-cols-2 gap-4">
                            <div>
                              <Label htmlFor="editFirstName">First Name</Label>
                              <Input id="editFirstName" name="firstName" value={editingUser?.firstName || ''} onChange={(e) => handleInputChange(e, false)} />
                            </div>
                            <div>
                              <Label htmlFor="editLastName">Last Name</Label>
                              <Input id="editLastName" name="lastName" value={editingUser?.lastName || ''} onChange={(e) => handleInputChange(e, false)} />
                            </div>
                          </div>
                          <div>
                            <Label htmlFor="editEmail">Email</Label>
                            <Input id="editEmail" name="email" type="email" value={editingUser?.email || ''} onChange={(e) => handleInputChange(e, false)} />
                          </div>
                          <div>
                            <Label htmlFor="editRole">Role</Label>
                            <Select onValueChange={(value) => handleSelectChange(value, 'role', false)} value={editingUser?.role}>
                              <SelectTrigger>
                                <SelectValue placeholder="Select a role" />
                              </SelectTrigger>
                              <SelectContent>
                                <SelectItem value={Role.DISPATCHER}>Dispatcher</SelectItem>
                                <SelectItem value={Role.DRIVER}>Driver</SelectItem>
                              </SelectContent>
                            </Select>
                          </div>
                          <div>
                            <Label htmlFor="editStatus">Status</Label>
                            <Select onValueChange={(value) => handleSelectChange(value, 'status', false)} value={editingUser?.status}>
                              <SelectTrigger>
                                <SelectValue placeholder="Select a status" />
                              </SelectTrigger>
                              <SelectContent>
                                <SelectItem value={Status.STANDBY}>Standby</SelectItem>
                                <SelectItem value={Status.BUSY}>Busy</SelectItem>
                                <SelectItem value={Status.OUTOFSERVICE}>Out of Service</SelectItem>
                              </SelectContent>
                            </Select>
                          </div>
                          <div>
                            <Label htmlFor="editBirthDate">Birth Date</Label>
                            <Input id="editBirthDate" name="birthDate" type="date" value={editingUser?.birthDate || ''} onChange={(e) => handleInputChange(e, false)} />
                          </div>
                        </div>
                        <Button onClick={handleUpdateUser} className="bg-[#F95738] hover:bg-[#F95738]/90">
                          Update User
                        </Button>
                      </DialogContent>
                    </Dialog>
                    <Button variant="destructive" size="sm" onClick={() => handleDeleteUser(user.id)}>Delete</Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}



