import {useMutation, useQuery} from "@tanstack/react-query";


interface APIResponse {
    message: string;
    statusCode: number;
}


export interface CarInfo {
    id: number,
    carName: string;
    pricePerDay: number;
}


const ERROR_MSG = 'Network response not ok'

export const useGetCarsData = () => {
    return useQuery<CarInfo[]>({
        queryKey: ['carsData'],
        queryFn: async () => {
            const res = await fetch('/api/v1/cars/list');
            if (!res.ok) {
                throw new Error(ERROR_MSG);
            }
            const json = await res.json();
            return json.data as CarInfo[];
        },
    });
};

interface Rental {
    id: number,
    pickUpDate: Date,
    returnDate: Date,
    car: CarInfo,
    totalRentalCost: number,
    driverName: string
}

export interface RentalInfo {
    rentals: Rental[],
    totalRevenue: number
}

export const useGetAllRentalsData = () => {
    return useQuery< RentalInfo>({
        queryKey: ['rentalsData'],
        queryFn: async () => {
            const res = await fetch("/api/v1/admin/list");
            if (!res.ok) {
                throw new Error(ERROR_MSG)
            }
            const json = await res.json()
            return json.data as RentalInfo;
        }
    })
}

export interface RentalRequest {
    carId: number,
    driverName: string,
    pickUpDate: Date,
    returnDate: Date,
    driverAge: number
}


export const usePutRentalMutation = () => {
    return useMutation<APIResponse, Error, RentalRequest>({
        mutationFn: async (data: RentalRequest) => {
            const res = await fetch("/api/v1/rental/rent", {
                method: "PUT",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(data),
            });
            const result = await res.json().catch(() => null);
            if (res.status !== 201) {
                return {
                    message: result?.error ?? (res.ok ? "Success": "Error"),
                    statusCode: res.status
                }
            }
            return {
                message: result?.data ?? (res.ok ? "Success": "Error"),
                statusCode: res.status
            }
        }
    });
};