
import { useSession } from "next-auth/react"

// Server-side session helper

// Client-side session hook
export function useUserSession() {
    const { data: session } = useSession()
    if (session && session.user) {
        return session

    }
    return 0;
}

// For cases where you need a synchronous mock (client-side only)
export function getMockSession() {
    return {
        user: {
            id: "mock-id",
            name: "Mock User",
            email: "mock@example.com",
        },
        accessToken: "mock-access-token",
        expires: new Date(Date.now() + 60 * 60 * 1000).toISOString(),
    }
}

// Helper to return session as JSON (if needed)
export function getUserSessionJson() {
    return JSON.stringify(getMockSession())
}
