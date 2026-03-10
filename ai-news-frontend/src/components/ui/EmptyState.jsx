export default function EmptyState({
    title = "No articles found",
    description = "Please check back later or try a different category"
}) {
    return (
        <div className="flex flex-col items-center justify-center py-16 text-center">
            <div className="w-12 h-12 rounded-full bg-slate-100 flex items-center justify-center mb-4">
                <span className="text-slate-400 text-xl">📰</span>
            </div>

            <h2 className="text-lg font-semibold text-slate-800 mb-2">
                {title}
            </h2>

            <p className="text-sm text-slate-500 max-w-sm">
                {description}
            </p>
        </div>
    );
}