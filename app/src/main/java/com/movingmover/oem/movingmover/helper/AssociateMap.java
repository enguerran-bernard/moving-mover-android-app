package com.movingmover.oem.movingmover.helper;

import java.util.HashMap;

public class AssociateMap<T, U> {
    private HashMap<T, U> mFirstHashmap;
    private HashMap<U, T> mSecondHashmap;

    public AssociateMap() {
        mFirstHashmap = new HashMap<>();
        mSecondHashmap = new HashMap<>();
    }

    public HashMap<T,U> getFirstHashMap() {
        return (HashMap<T, U>) mFirstHashmap.clone();
    }

    public HashMap<U,T> getSecondHashmap() {
        return (HashMap<U, T>) mSecondHashmap.clone();
    }

    public void put(T t, U u) {
        U firstAssociate = mFirstHashmap.get(t);
        if(firstAssociate != null) {
            mSecondHashmap.remove(firstAssociate);
        }
        T secondAssociate = mSecondHashmap.get(u);
        if(secondAssociate != null) {
            mFirstHashmap.remove(secondAssociate);
        }
        mFirstHashmap.put(t, u);
        mSecondHashmap.put(u, t);
    }

    public void remove(T t, U u) {
        if(t != null) {
            U firstAssociate = mFirstHashmap.remove(t);
            if(firstAssociate != null) {
                mSecondHashmap.remove(firstAssociate);
            }
        }
        if(u != null) {
            T secondAssociate = mSecondHashmap.remove(u);
            if(secondAssociate != null) {
                mFirstHashmap.remove(secondAssociate);
            }
        }
    }


}
