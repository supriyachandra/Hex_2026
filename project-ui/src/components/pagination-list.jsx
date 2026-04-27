import axios from "axios"
import { useEffect, useState } from "react"

export function PaginationList(){
    const[currentPage, setCurrentPage]= useState(0)
    const[list, setList]= useState([])
    const limit=5

    const api= `https://stephen-king-api.onrender.com/api/books?page=${currentPage}&limit=${limit}`

    useEffect(()=> {

        const fetchList= async ()=>{
            try{
                const response= await axios.get(api)
                setList(response.data.data)
                //console.log(response.data.data)
            }catch(err){
                console.log(err)
            }
        }
        fetchList()

    }, [currentPage])

    return (
        <div className="container-fluid p-4">
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">Title</th>
                        <th scope="col">Year</th>
                        <th scope="col">Publisher</th>
                        <th scope="col">ISBN</th>
                    </tr>
                </thead>

                <tbody>
                    {
                        list.map((l, index)=> (
                            <tr key={index}>
                                <th>{l.Title}</th>
                                <th>{l.Year}</th>
                                <th>{l.Publisher}</th>
                                <th>{l.ISBN}</th>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

            <ul className="pagination">
                {/* previous */}
                <li className={`page-item ${currentPage===0? "disabled": ""}`}>
                    <button className="page-link"
                    onClick={()=> setCurrentPage(p=> p-1)}>
                        Previous
                    </button>
                </li>

                {
                    [...Array(5)].map((_, i) => (
                        <li key={i}
                        className={`page-item ${currentPage === i? "active": ""}`}>
                            <button className="page-link"
                            onClick={()=> setCurrentPage(i)}>
                                {i+1}
                            </button>
                        </li>
                    ))
                }

                {/* next */}
                <li className={`page-item ${list.length<5?"disabled": ""}`}>
                    <button className="page-link"
                    onClick={()=> setCurrentPage(p=> p+1)}>
                        Next
                    </button>
                </li>
            </ul>
        </div>
    )
}