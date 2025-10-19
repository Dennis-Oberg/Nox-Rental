import React from "react";
import {useGetAllRentalsData} from "../hooks/apiHooks";
import Loading from "../components/Loading";
import Error from "../components/Error";

const AdminPage = () => {
    const {data, isLoading, isError} = useGetAllRentalsData()

    if (isError) {
        return(
            <Error errorText={"Failed to fetch data"} />
        )
    }

    if (isLoading) {
        return (
            <Loading loadingText={"Loading rentals information"}/>
        )
    }


    return (
        <div className={"parent"}>
            <h2>Summary</h2>
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Car</th>
                    <th>From date</th>
                    <th>To date</th>
                    <th>Rent cost (SEK)</th>
                </tr>
                </thead>
                <tbody>
                {data &&
                    data.rentals.map((rental, index) => (
                        <tr key={index}>
                            <td>{rental.driverName}</td>
                            <td>{rental.car?.carName}</td>
                            <td>
                                {new Date(rental.pickUpDate).toLocaleDateString()}
                            </td>
                            <td>
                                {new Date(rental.returnDate).toLocaleDateString()}
                            </td>
                            <td>{rental.totalRentalCost}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className={"rentalsDiv"}>
                <span>
                     <b> Total revenue: {data && data.totalRevenue}</b>
                </span>
            </div>
        </div>
    )
}

export default AdminPage