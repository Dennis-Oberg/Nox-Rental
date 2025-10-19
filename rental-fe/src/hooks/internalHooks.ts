import {useEffect, useState} from "react";
import {CarInfo, RentalRequest} from "./apiHooks";
import {UseFormWatch} from "react-hook-form";

interface UseRentalPriceParams {
    watch: UseFormWatch<RentalRequest>;
    carsData: CarInfo[] | undefined;
}

const MILLIS_IN_ONE_DAY = 1000 * 60 * 60 * 24;

export const useRentalPrice = ({watch, carsData}: UseRentalPriceParams) => {
    const watchCarId = watch('carId');
    const watchPickUpDate = watch('pickUpDate');
    const watchReturnDate = watch('returnDate');

    const [totalPrice, setTotalPrice] = useState<number | null>(null);

    useEffect(() => {
        if (watchCarId && watchPickUpDate && watchReturnDate && carsData) {
            const car = carsData.find(c => c.id === Number(watchCarId));
            if (!car) return;

            const start = new Date(watchPickUpDate);
            const end = new Date(watchReturnDate);
            const diffTime = end.getTime() - start.getTime();
            const diffDays = Math.ceil(diffTime / MILLIS_IN_ONE_DAY);

            setTotalPrice(diffDays > 0 ? diffDays * car.pricePerDay : 0);
        } else {
            setTotalPrice(null);
        }
    }, [watchCarId, watchPickUpDate, watchReturnDate, carsData]);

    return totalPrice;
};
