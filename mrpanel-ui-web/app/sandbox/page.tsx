"use client"
import { useMemo, useState } from "react";
import MRTable from "@/components/table/Table"
import { MRTableColumn } from "@/components/table/Table.types";
import mockData from "./MOCK_DATA.json";

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

const ITEMS_PER_PAGE = 10;

export const Sandbox = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [selectedRowIds, setSelectedRowIds] = useState<Set<string>>(new Set());

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

    const totalPages = Math.ceil(members.length / ITEMS_PER_PAGE);

    const paginatedData = useMemo(() => {
      const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
      const endIndex = startIndex + ITEMS_PER_PAGE;
      return members.slice(startIndex, endIndex);
    }, [members, currentPage]);

    const columns: MRTableColumn<Member>[] = useMemo(
      () => [
        { id: "first_Name", header: "First Name" },
        { id: "last_Name", header: "Last Name" },
        { id: "age", header: "Age" },
        { id: "visits", header: "Visits" },
        { id: "progress", header: "Progress" },
        { id: "status", header: "Status" },
      ],
      []
    );

    const handleRowToggle = (rowId: string, selected: boolean) => {
      setSelectedRowIds(prev => {
        const newSet = new Set(prev);
        if (selected) {
          newSet.add(rowId);
        } else {
          newSet.delete(rowId);
        }
        return newSet;
      });
    };

    return(
        <div style={{margin: "5%"}}>
            <MRTable 
              data={paginatedData} 
              columns={columns} 
              table_name="asd"
              showSelection={true}
              selectedRowIds={selectedRowIds}
              onRowToggle={handleRowToggle as (rowId: string, selected: boolean) => void}
              getRowId={(row: Member, index: number): string => `${row.first_Name}-${row.last_Name}-${index}`}
              pagination={{
                currentPage: currentPage,
                totalPages: totalPages,
                onPageChange: setCurrentPage,
              }}
            />
        </div>
    )
}

export default Sandbox