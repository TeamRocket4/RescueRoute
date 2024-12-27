import { Metadata } from "next"
import { SidebarNav } from "@/components/sidebar-nav"

export const metadata: Metadata = {
  title: "Dashboard",
  description: "Dispatcher Dashboard",
}

const sidebarNavItems = [
  {
    title: "Users",
    href: "/dashboard/users",
  },
  {
    title: "Hospitals",
    href: "/dashboard/hospitals",
  },
  {
    title: "Map",
    href: "/dashboard/map",
  },
]

interface DashboardLayoutProps {
  children: React.ReactNode
}

export default function DashboardLayout({
  children,
}: DashboardLayoutProps) {
  return (
    <div className="flex h-screen bg-gray-100">
      <aside className="w-64 bg-white shadow-md">
        <div className="flex h-20 items-center justify-center">
          <h1 className="text-2xl font-bold text-[#6C63FF]">Dispatcher</h1>
        </div>
        <SidebarNav items={sidebarNavItems} />
      </aside>
      <main className="flex-1 overflow-y-auto p-8">
        <div className="rounded-lg bg-white p-6 shadow-md">
          {children}
        </div>
      </main>
    </div>
  )
}

