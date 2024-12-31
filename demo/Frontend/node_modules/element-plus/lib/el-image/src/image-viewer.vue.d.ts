import { PropType } from 'vue';
declare const _default: import("vue").DefineComponent<{
    urlList: {
        type: PropType<string[]>;
        default: () => any[];
    };
    zIndex: {
        type: NumberConstructor;
        default: number;
    };
    onSwitch: {
        type: FunctionConstructor;
        default: () => {};
    };
    onClose: {
        type: FunctionConstructor;
        default: () => {};
    };
    initialIndex: {
        type: NumberConstructor;
        default: number;
    };
}, {
    index: import("vue").Ref<number>;
    wrapper: any;
    img: any;
    infinite: boolean;
    loading: boolean;
    isSingle: import("vue").ComputedRef<boolean>;
    isFirst: import("vue").ComputedRef<boolean>;
    isLast: import("vue").ComputedRef<boolean>;
    currentImg: import("vue").ComputedRef<string>;
    imgStyle: import("vue").ComputedRef<{
        transform: string;
        transition: string;
        'margin-left': string;
        'margin-top': string;
    }>;
    mode: import("vue").Ref<{
        name: string;
        icon: string;
    }>;
    handleActions: (action: any, options?: {}) => void;
    prev: () => void;
    next: () => void;
    hide: () => void;
    toggleMode: () => void;
    handleImgLoad: () => void;
    handleImgError: (e: any) => void;
    handleMouseDown: (e: any) => void;
}, unknown, {}, {}, import("vue").ComponentOptionsMixin, import("vue").ComponentOptionsMixin, Record<string, any>, string, import("vue").VNodeProps & import("vue").AllowedComponentProps & import("vue").ComponentCustomProps, Readonly<{
    urlList: string[];
    zIndex: number;
    onSwitch: Function;
    onClose: Function;
    initialIndex: number;
} & {}>, {
    urlList: string[];
    zIndex: number;
    onSwitch: Function;
    onClose: Function;
    initialIndex: number;
}>;
export default _default;
