export default function Loader() {
  return (
    <div className="py-2 flex justify-center">
      <div className="flex flex-col items-center gap-3">
        <div className="w-8 h-8 border-4 border-slate-200 border-t-indigo-600 rounded-full animate-spin"></div>
        <p className="text-sm text-slate-500">
            Loading articles...
        </p>
      </div>
    </div>
  );
}
