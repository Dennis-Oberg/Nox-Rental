import React, {useState} from "react";
import { useGetCarsData, usePutRentalMutation} from "../hooks/apiHooks";
import {RentalRequest} from "../types/types";
import Loading from "../components/Loading";
import {SubmitHandler, useForm} from "react-hook-form"
import {useRentalPrice} from "../hooks/internalHooks";
import TextFeedbackError from "../components/TextFeedbackError";
import Error from "../components/Error";

const RentalPage = () => {

    const {isLoading, data: getCarsData, isError: carsDataError} = useGetCarsData();
    const {mutateAsync: rentalMutation, isPending: rentalMutationPending} = usePutRentalMutation();
    const [response, setServerResponse] = useState<string | undefined>(undefined);

    const {
        register,
        handleSubmit,
        reset,
        watch,
        formState: {errors},
    } = useForm<RentalRequest>();

    //hook that provide our "totals-cost"
    const totalRentalPrice = useRentalPrice({watch, carsData: getCarsData});


    const onSubmit: SubmitHandler<RentalRequest> = async (data) => {
        if (!data.carId) {
            return;
        }
        const driverAgeToNumber = Number(data.carId)
        const res = await rentalMutation({
                carId: driverAgeToNumber,
                driverName: data.driverName,
                pickUpDate: data.pickUpDate,
                returnDate: data.returnDate,
                driverAge: data.driverAge
            }
        );
        const statusCode = res.statusCode;
        if (statusCode !== 201) {
            setServerResponse(res.message);
            return
        }
        setServerResponse(res.message);
        reset();
    }
    if (isLoading) {
        return <Loading loadingText={"Fetching rentals"}/>
    }
    if (carsDataError) {
        return <Error errorText={"Failed to fetch cars."}/>
    }

    return (
        <div className={"parent"}>
            <h2>Rent a car!</h2>
            {response && <h2>{response}</h2>}
            <div className={"formContainer"}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={"inputContainer"}>
                        <label htmlFor="">Select car.</label>
                        <select {...register('carId', {required: true})}>

                            {getCarsData && getCarsData.map((car, index) => {
                                return <option key={index} value={car.id}>Car
                                    name: {car.carName}, Price:{car.pricePerDay} SEK/day</option>
                            })}
                        </select>
                        {errors.carId && <TextFeedbackError text={"Choose a car."}/>}
                    </div>

                    <div className={"inputContainer"}>
                        <label htmlFor="">Select when you wish to fetch the car.</label>
                        <input type={"date"}  {...register('pickUpDate', {required: true})} />
                        {errors.pickUpDate && <TextFeedbackError text={"Select a date to pick up the car."}/>}
                    </div>

                    <div className={"inputContainer"}>
                        <label htmlFor="">Select when you wish to return the car.</label>
                        <input type={"date"}  {...register('returnDate', {required: true})} />
                        {errors.returnDate && <TextFeedbackError text={"Select a date to return the car."}/>}
                    </div>

                    <div className={"inputContainer"}>
                        <label htmlFor="">What is your name?</label>
                        <input type={"text"}
                               placeholder={"Provide your name!"} {...register('driverName', {required: true})} />
                        {errors.driverName && <TextFeedbackError text={"Please provide your name."}/>}
                    </div>
                    <div className={"inputContainer"}>
                        <label htmlFor="">What is your age?</label>
                        <input type={"text"}  {...register('driverAge', {required: true})} />
                        {errors.driverAge && <TextFeedbackError text={"Please provide your age."}/>}
                    </div>
                    {totalRentalPrice && <span>Price {totalRentalPrice}SEK</span>}
                    <div className={"inputContainer"}>
                        <input type={"submit"} value={rentalMutationPending ? "Creating Rental..." : "Rent"}
                               disabled={rentalMutationPending}/>
                    </div>
                </form>
            </div>
        </div>
    )
}
export default RentalPage;