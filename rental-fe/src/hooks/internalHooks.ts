import {useEffect, useState} from "react";
import {UseFormWatch} from "react-hook-form";
import {CarInfo, RentalRequest} from "../types/types";

interface UseRentalPriceParams {
    watch: UseFormWatch<RentalRequest>;
    carsData: CarInfo[] | undefined;
}

const MILLIS_IN_ONE_DAY = 1000 * 60 * 60 * 24; //one day in misslis

export const useRentalPrice = ({watch, carsData}: UseRentalPriceParams) => {
    const watchCarId = watch('carId'); //react-hook-form shenanigans
    const watchPickUpDate = watch('pickUpDate'); //react-hook-form shenanigans
    const watchReturnDate = watch('returnDate'); //react-hook-form shenanigans

    const [totalPrice, setTotalPrice] = useState<number | null>(null);

    useEffect(() => {
        if (watchCarId && watchPickUpDate && watchReturnDate && carsData) {
            const car = carsData.find(c => c.id === Number(watchCarId)); //get the selected car from the array
            if (!car) return;

            const start = new Date(watchPickUpDate);
            const end = new Date(watchReturnDate);
            const diffTime = end.getTime() - start.getTime();
            const diffDays = Math.ceil(diffTime / MILLIS_IN_ONE_DAY);

            //if diff in days is more than 0, then give us
            //the price for that back.
            setTotalPrice(diffDays > 0 ? diffDays * car.pricePerDay : 0);

        } else {
            setTotalPrice(null);
        }
    }, [watchCarId, watchPickUpDate, watchReturnDate, carsData]);

    return totalPrice;
};
