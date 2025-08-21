import { PrismaClient } from "@prisma/client"

declare global {
  // allow globalThis.prisma
  var prisma: PrismaClient
}

