"use client"
import { useMemo } from "react";
import MRTable from "@/components/table/Table"
import mockData from "./MOCK_DATA.json"; // import local JSON

// To create a table we need: a type, some typed Data,
// an array of Strings which correspond to headers, and et voila

type Member = {
  first_Name: string;
  last_Name: string;
  age: number;
  visits: number;
  progress: number;
  status: string;
};

export const Sandbox = () => {
    // completely unneeded, but fun!
    const members: Member[] = useMemo(
      () =>
        (mockData as Array<{ first_name: string; last_name: string; age: number; visits: number; progress: number; status: string }>).map((m) => ({
          first_Name: m.first_name,
          last_Name: m.last_name,
          age: m.age,
          visits: m.visits,
          progress: m.progress,
          status: m.status,
        })),
      []
    );

    // keep same format as before: array of JSON strings
    const data = useMemo<string[]>(
      () => members.map((m) => JSON.stringify(m)),
      [members]
    );

    const columns = ["first_Name", "last_Name", "age", "visits", "progress", "status"];
    
    return(
        <div>
            <MRTable data={data} columns={columns} table_name={"asd"}></MRTable>
        </div>
    )
}

export default Sandbox