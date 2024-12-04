"use client"

import * as React from "react"
import { useRouter } from "next/navigation"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import apiClient from "@/lib/api-client"


interface UserAuthFormProps extends React.HTMLAttributes<HTMLDivElement> { }

export function UserAuthForm({ className, ...props }: UserAuthFormProps) {
  const router = useRouter()
  const [isLoading, setIsLoading] = React.useState<boolean>(false)

  const [email, setEmail] = React.useState<String>("");
  const [password, setPassword] = React.useState<String>("");

  async function onSubmit(event: React.SyntheticEvent) {
    event.preventDefault()
    setIsLoading(true)
    await apiClient.post("/api/auth/login", {
      "email": email,
      "password": password
    }).then((res) => {
      console.log(res.data)
      //router.push("/dashboard/users")
    }).catch((err) => {
      setIsLoading(false)
      console.log(err.message)
    })
  }

  const handleInputChangeEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value
    setEmail(value);
  }

  const handleInputChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value
    setPassword(value);
  }

  return (
    <div className={cn("grid gap-6", className)} {...props}>
      <form onSubmit={onSubmit}>
        <div className="grid gap-2">
          <div className="grid gap-1">
            <Label className="sr-only" htmlFor="email">
              Email
            </Label>
            <Input
              id="email"
              placeholder="name@example.com"
              type="email"
              autoCapitalize="none"
              autoComplete="email"
              autoCorrect="off"
              disabled={isLoading}
              onChange={(e) => handleInputChangeEmail(e)}
            />
          </div>
          <div className="grid gap-1">
            <Label className="sr-only" htmlFor="password">
              Password
            </Label>
            <Input
              id="password"
              placeholder="Password"
              type="password"
              autoCapitalize="none"
              autoComplete="current-password"
              autoCorrect="off"
              disabled={isLoading}
              onChange={(e) => handleInputChangePassword(e)}
            />
          </div>
          <Button disabled={isLoading} className="bg-[#F95738] hover:bg-[#F95738]/90">
            {isLoading ? "Signing In..." : "Sign In"}
          </Button>
        </div>
      </form>
    </div>
  )
}

