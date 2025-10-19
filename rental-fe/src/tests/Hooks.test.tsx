import {renderHook} from "@testing-library/react";

import {useRentalPrice} from "../hooks/internalHooks";
import {CarInfo, RentalRequest} from "../types/types";

const expectedResult: CarInfo[] = [
    {id: 1, carName: "Volvo S60", pricePerDay: 1500},
    {id: 2, carName: "Volkswagen Golf", pricePerDay: 1333},
    {id: 3, carName: "Ford Mustang", pricePerDay: 3000},
    {id: 4, carName: "Ford Transit", pricePerDay: 2400},
];




describe('testing rental price hook', () => {
    const carsData: CarInfo[] = [
        {id: 1, carName: 'Volvo', pricePerDay: 200},
        {id: 2, carName: 'Ford', pricePerDay: 400}
    ]

    test('returns null if no dates or cars are selected', () => {
        const watchMock = () => undefined;
        const {result} = renderHook(() =>
            useRentalPrice({watch: watchMock as any, carsData})
        );
        expect(result.current).toBeNull()
    })
    test('calculate price for car nmr 1 and price 200', () => {
        const watchMock = (field: keyof RentalRequest) => {
            if (field === "carId") return "1";
            if (field === "pickUpDate") return "2025-10-15";
            if (field === "returnDate") return "2025-10-17";
        };
        const {result} = renderHook(() =>
            useRentalPrice({watch: watchMock as any, carsData}));
        expect(result.current).toBe(400)
    })

    test('calculate price for car nmbr 2 and price 400', () => {
        const watchMock = (field: keyof RentalRequest) => {

            if (field === 'carId') return "2";
            if (field === "pickUpDate") return "2025-10-12";
            if (field === "returnDate") return "2025-10-17";
        };
        const {result} = renderHook(() => useRentalPrice({watch: watchMock as any, carsData}));
        expect(result.current).toBe(2000)

    })
})




